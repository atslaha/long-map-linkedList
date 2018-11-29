package il.co.longmaplinkedlist;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LongMapImpl<V> implements LongMap<V> {

   private int counter = 0;
   private float loadFactor = 0.75f;
   private int TABLE_SIZE = 16;
   private Object[] hashTable = new Object[TABLE_SIZE];

   static class Entry<V>{
       Long key;
       V value;

       public Entry(Long key, V value){
           this.key=key;
           this.value=value;
       }
   }

    public int hash(Long key){
        return Math.abs(key.hashCode()) % TABLE_SIZE-1;
    }


    public void increaseTableSize(){
        if(counter>TABLE_SIZE*loadFactor) {
            TABLE_SIZE = (int) (TABLE_SIZE*1.5f);
            hashTable = Arrays.copyOf(hashTable, TABLE_SIZE);
        }
    }

    public V put(long key, V value) {
       int hash = hash(key);
       Entry newEntry = new Entry(key,value);
       if (hashTable[hash]==null){
           List newEntryLinkedList = new LinkedList();
           newEntryLinkedList.add(newEntry);
           hashTable[hash] = newEntryLinkedList;
           counter++;
           increaseTableSize();
       }else {
           List currentLinkedList = (LinkedList) hashTable[hash];
           Entry curentEntry;
           for(int i=0;i<currentLinkedList.size();i++){
               curentEntry = (Entry) currentLinkedList.get(i);
               if(curentEntry.value.equals(newEntry.value)){
                   currentLinkedList.remove(i);
                   currentLinkedList.add(i,newEntry);
                   hashTable[hash] = currentLinkedList;
               }else{
                   currentLinkedList.add(newEntry);
                   counter++;
                   increaseTableSize();
               }
           }
       }
       return (V) newEntry.value;
    }

    public V get(long key) {
       int hash = hash(key);
       Entry<V> entry;
       List bucketList = new LinkedList();
       if(hashTable[hash] == null){
           return null;
       }else {
           bucketList = (LinkedList) hashTable[hash];
           for (int i = 0; i < bucketList.size(); i++) {
               entry = (Entry<V>) bucketList.get(i);
               if (entry.key == key) {
                   return entry.value;
               }
           }
       return null;
       }
    }

    public V remove(long key) {
        int hash = hash(key);
        Entry<V> entry;
        List bucketList = new LinkedList();
        if(hashTable[hash] == null){
            return null;
        }else {
            bucketList = (LinkedList) hashTable[hash];
            for (int i = 0; i < bucketList.size(); i++) {
                entry = (Entry<V>) bucketList.get(i);
                if (entry.key == key) {
                    bucketList.remove(i);
                    counter--;
                    return entry.value;
                }
            }
            return null;
        }
    }

    public boolean isEmpty() {
       List bucketList = new LinkedList();
       for(int i = 0; i<hashTable.length; i++){
           if(hashTable[i] != null){
               bucketList = (LinkedList) hashTable[i];
               if(!(bucketList.isEmpty())){
               return false;
               }
           }
       }
       return true;
    }

    public boolean containsKey(long key) {
       List bucketList = new LinkedList();
       Entry entry;
       int hash = hash(key);
       if(hashTable[hash] != null){
           bucketList = (LinkedList) hashTable[hash];
           for(int y = 0; y<bucketList.size(); y++){
               entry = (Entry) bucketList.get(y);
               if(entry.key.equals(key)){
                    return true;
               }
           }
       }
        return false;
    }

    public boolean containsValue(V value) {
       List bucketList = new LinkedList();
       Entry entry;
       for(int i = 0; i<hashTable.length; i++){
           if(hashTable[i] != null){
               bucketList = (LinkedList) hashTable[i];
               for(int y = 0; y<bucketList.size(); y++){
                   entry = (Entry) bucketList.get(y);
                   if(entry.value.equals(value)){
                       return true;
                   }
               }
           }
       }
        return false;
    }

    public long[] keys() {
       Entry entry;
       List bucketList = new LinkedList();
       long[] keyList = new long[counter];
       for(int i = 0; i<hashTable.length; i++){
           if(hashTable[i] != null){
               bucketList = (LinkedList) hashTable[i];
               for(int y = 0; y<bucketList.size(); y++){
                   entry = (Entry) bucketList.get(y);
                   keyList[y] = entry.key;
               }
           }
       }
        return keyList;
    }

    public V[] values() {
       V[] arrayValues =  (V[]) new Object[counter];
       Entry entry;
       List bucketList = new LinkedList();
       for(int i = 0; i<hashTable.length; i++){
           if(hashTable[i] != null){
               bucketList = (LinkedList) hashTable[i];
               for(int y = 0; y<bucketList.size(); y++){
                   entry = (Entry) bucketList.get(y);
                   arrayValues[y] = (V) entry.value;
               }
           }
       }
        return arrayValues;
    }

    public long size() {
        return TABLE_SIZE;
    }

    public void clear() {
       for(int i = 0; i<hashTable.length; i++){
           hashTable[i] = null;
       }

    }

    public void display(){
       List curent = new LinkedList();
       Entry entry;
       for(int i=0; i<hashTable.length; i++){
           if(hashTable[i]!=null){
               curent = (LinkedList) hashTable[i];
               for (int y=0;y<curent.size();y++){
                   entry = (Entry) curent.get(y);
                   System.out.println("{"+entry.key+"="+entry.value+"}" +" ");
               }

           }
        }
    }
}
