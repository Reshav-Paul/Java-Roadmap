import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapTutorial {
    public static void main(String[] args) {
        
        int keys[] = {1, 3, 2, 4, 5};
        String values[] = {"One", "Two", "Three", "Four", "Five"};

        Map<Integer, String> hashMap = new HashMap<>();
        for(int key: keys){
            hashMap.put(key, values[key - 1]);
        }
        System.out.println("HashMap:");
        for(int key: hashMap.keySet()){
            System.out.println(key + ": " + hashMap.get(key));
        }
        
        //Some of the functions provided by different kinds of Maps are
        System.out.println("HashMap empty? " + hashMap.isEmpty());
        System.out.println("key 3 present? " + hashMap.containsKey(3));
        System.out.println("value \"Six\" present? " + hashMap.containsValue("Six"));
        System.out.println("Size: " + hashMap.size());
        System.out.println("Key Set: " + hashMap.keySet());
        System.out.println("Values: " + hashMap.values());
        System.out.println("Removed: " + hashMap.remove(4));
        System.out.println("Remove entry {4: \"Five\"} if present: " + hashMap.remove(4, "Five"));
        System.out.println("Map after deletion: " + hashMap);

        //Linked HashMap preserves the order in which the elements were entered
        System.out.println("LinkedHashMap:");
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for(int key: keys){
            linkedHashMap.put(key, values[key - 1]);
        }
        for(int key: linkedHashMap.keySet()){
            System.out.println(key + ": " + linkedHashMap.get(key));
        }
        
        //TreeMap stores the records in natural order of keys
        System.out.println("TreeMap:");
        Map<Integer, String> treeMap = new TreeMap<>();
        for(int key: keys){
            treeMap.put(key, values[key - 1]);
        }
        //Another technique to retrieve map entries
        for(Map.Entry<Integer, String> mapEntry: treeMap.entrySet()){
            System.out.println(mapEntry.getKey() + ": " + mapEntry.getValue());
        }
    }    
}