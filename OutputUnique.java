import java.io.*;
import java.util.*;

class OutputUnique {
    public static void main(String args[]) throws Exception {
        String barcode="";
        for (String x: args) {
            String delimiter = " ";
            String[] temp = x.split(delimiter);
            barcode = temp[0];
        }
        for(int i=0; i<2; i++){
          int numBarcode = 2;
          String[] forward;
          String[] reverse;
          forward = new String[numBarcode];
          reverse = new String[numBarcode];
          forward[0] = "NAME 1 HERE";
          forward[numBarcode-1] = "NAME 2 HERE";
          reverse[0] = "NAME 1 HERE";
          reverse[numBarcode-1] = "NAME 2 HERE";
        String dir1 = "~/directory";
        String trueseq = "";
        String readid = "";
        String barcoderef = "";
        String umi = "";
        String read = "";
        String qual = "";
        String strand = "";
        String sequence = "";
        Map countdict = new HashMap();
        Map newseqdict = new HashMap();
        Set indices = new HashSet();
        FileReader fr1 = new FileReader(dir1 + "forward[i]");
        FileReader fr2 = new FileReader(dir1 + "reverse[i]");
        BufferedReader br1 = new BufferedReader(fr1);
        BufferedReader br2 = new BufferedReader(fr2);
        int index = 0;
        int position = 0;
        int counter=0;
        int thresh = 75;
        while(true) {
            String s1 = br1.readLine();
            String s2 = br2.readLine();
            if (s1 == null || s2 == null)
            break;
            position = counter%4;
            if (position == 0)
            { readid = s2;}
            if (position == 1)
			{
            if (s1.length() > 11)
            { sequence = s1;
              trueseq = s2;
              barcoderef = s1.substring(5,11);
              umi = s1.substring(0,5);
              read = umi + barcoderef + trueseq;
            } else {
			  read = "NNNNNNNNNNN";
			}
			}
            if (position == 2)
            { strand = s2;}
            if (position == 3)
            {
            if (s2.length() > thresh)
                {
                 qual = s2.substring(5,thresh);
                if (barcoderef.equals(barcode))
                    {
                     newseqdict.put(read, readid + "\n" + trueseq.substring(5,thresh) + "\n" + "+" + "\n" + qual + "\n");
                    }
                }
            }
           counter = counter + 1;
        }
fr1.close();
fr2.close();
FileWriter f0 = new FileWriter(dir1 + forward[i] + barcode + "_nodupe_trimmed.fastq");
Set entries = newseqdict.entrySet();
Iterator iterator = entries.iterator();
while(iterator.hasNext()) {
    Map.Entry entry = (Map.Entry) iterator.next();
    String fastq = (String)entry.getValue();
    f0.write(fastq);
}
f0.close();
}
}
}
