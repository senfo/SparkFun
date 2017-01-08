import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.bson.Document;
import scala.Tuple2;

import java.text.NumberFormat;
import java.util.*;

/**
 * A really simple map/reduce example using data from MongoDB
 */
public class MongoMapReduce {
    public static void main(String[] args) {
        //SparkConf conf = new SparkConf().setAppName("MongoMapReduce").setMaster("spark://localhost:7077");
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("TwitterSpark");
        JavaSparkContext context = new JavaSparkContext(conf);
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("large");
        MongoCollection<Document> zips = database.getCollection("zips");
        MongoCursor<Document> cursor = zips.find().iterator();
        List<Document> documents = new ArrayList<>();

        while (cursor.hasNext()) {
            documents.add(cursor.next());
        }

        JavaRDD<Document> rddDocuments = context.parallelize(documents);
        JavaPairRDD<String, Double> pairs = rddDocuments.mapToPair(document -> {
            String state = document.getString("state");
            Double population = document.getDouble("pop");

            return new Tuple2<>(state, population);
        });

        JavaPairRDD<String, Double> statePopulations = pairs.reduceByKey((a, b) -> a + b);
        Map<String, Double> sortedByPopulation = sortByValue(statePopulations.collectAsMap());

        //statePopulations.foreach((pair) -> System.out.println(String.format("%s: %1.0f", pair._1(), pair._2())));
        sortedByPopulation.forEach((k, v) -> System.out.println(String.format("%s: %s", k, NumberFormat.getInstance(Locale.US).format(v))));
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, Comparator.comparing(o -> (o.getValue())));
        Map<K, V> result = new LinkedHashMap<>();

        for (Map.Entry<K, V> entry : list) {
            result.put( entry.getKey(), entry.getValue() );
        }

        return result;
    }
}