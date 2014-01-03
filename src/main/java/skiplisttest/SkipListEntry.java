package skiplisttest;

public class SkipListEntry {
    public static String negInf = "-∞";
    public static String posInf = "+∞";
    public String key;
    public Integer value;
    public SkipListEntry up, down, left, right;
    public int pos;      // I added this to print the skiplist "nicely"

    public SkipListEntry(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
