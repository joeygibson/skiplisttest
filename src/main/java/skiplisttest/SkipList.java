package skiplisttest;

import java.util.Random;

public class SkipList {
    public SkipListEntry head, tail;

    public int n;   // number of entries in list
    public int h;   // height of the list
    public Random r;

    public SkipList() {
        SkipListEntry p1 = new SkipListEntry(SkipListEntry.negInf, null);
        SkipListEntry p2 = new SkipListEntry(SkipListEntry.posInf, null);

        p1.right = p2;
        p2.left = p1;

        head = p1;
        tail = p2;

        n = 0;
        h = 0;

        r = new Random();
    }

    public SkipListEntry findEntry(String k) {
        SkipListEntry p;

        p = head;

        while (true) {
            // Search right until you find a larger entry
            while (p.right.key != SkipListEntry.posInf &&
                    p.right.key.compareTo(k) <= 0) {
                p = p.right;
            }

            // Go down one level, if you can
            if (p.down != null) {
                p = p.down;
            } else {
                break;  // We've reached the lowest level
            }
        }

        return p;
    }

    public Integer get(String k) {
        SkipListEntry p;

        p = findEntry(k);

        if (k.equals(p.key)) {
            return p.value;
        } else {
            return null;
        }
    }

    public Integer put(String k, Integer v) {
        SkipListEntry p, q;
        int i;

        p = findEntry(k);

        if (k.equals(p.key)) {
            Integer old = p.value;

            p.value = v;

            return old;
        }

        // Not found...
        q = new SkipListEntry(k, v);

        q.left = p;
        q.right = p.right;
        p.right.left = q;
        p.right = q;

        i = 0;

        while (r.nextDouble() < 0.5 /* Coin toss */) {
            // Coin toss success ! ---> build one more level !!!

            if (i >= h) {
                SkipListEntry p1, p2;

                //Make the -oo and +oo entries
                p1 = new SkipListEntry(SkipListEntry.negInf, null);
                p2 = new SkipListEntry(SkipListEntry.posInf, null);

                // Link them
                p1.right = p2;
                p1.down = head;

                p2.left = p1;
                p2.down = tail;

                head.up = p1;
                tail.up = p2;

                // Update head and tail

                head = p1;
                tail = p2;

                h = h + 1;         // One more level...
            }

            // Find first element with an up link
            while (p.up == null) {
                p = p.left;
            }

            p = p.up;

            SkipListEntry e;

            e = new SkipListEntry(k, null);

            e.left = p;
            e.right = p.right;
            e.down = q;

            p.right.left = e;
            p.right = e;
            q.up = e;

            q = e;

            i += 1;
        }

        n += 1;

        return null;
    }

    public void printHorizontal()
    {
        String s = "";
        int i;

        SkipListEntry p;

     /* ----------------------------------
	Record the position of each entry
	---------------------------------- */
        p = head;

        while ( p.down != null )
        {
            p = p.down;
        }

        i = 0;
        while ( p != null )
        {
            p.pos = i++;
            p = p.right;
        }

     /* -------------------
	Print...
	------------------- */
        p = head;

        while ( p != null )
        {
            s = getOneRow( p );
            System.out.println(s);

            p = p.down;
        }
    }

    public String getOneRow( SkipListEntry p )
    {
        String s;
        int a, b, i;

        a = 0;

        s = "" + p.key;
        p = p.right;


        while ( p != null )
        {
            SkipListEntry q;

            q = p;
            while (q.down != null)
                q = q.down;
            b = q.pos;

            s = s + " <-";


            for (i = a+1; i < b; i++)
                s = s + "--------";

            s = s + "> " + p.key;

            a = b;

            p = p.right;
        }

        return(s);
    }

    public void printVertical()
    {
        String s = "";

        SkipListEntry p;

        p = head;

        while ( p.down != null )
            p = p.down;

        while ( p != null )
        {
            s = getOneColumn( p );
            System.out.println(s);

            p = p.right;
        }
    }


    public String getOneColumn( SkipListEntry p )
    {
        String s = "";

        while ( p != null )
        {
            s = s + " " + p.key;

            p = p.up;
        }

        return(s);
    }
}
