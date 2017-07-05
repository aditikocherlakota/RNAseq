//aditi kocherlakota corrected barcode scanner 7/05/17
import java.io.*;
import java.util.*;
import java.util.Scanner;

class OutputUnique {
    public static void main(String args[]) throws Exception {
        String barcode="";
        for (String x: args) {
            String delimiter = " ";
            String[] temp = x.split(delimiter);
        }

        Scanner scan= new Scanner(System.in);
         System.out.print("what is the directory to your run folder?\n");
         String dir1 = scan.next();
         System.out.print("What is the name of your forward file?\n");
         String forward = scan.next();
          System.out.print("What is the name of your reverse file?\n");
         String reverse = scan.next();
           System.out.print("What would you like to label your demultiplexed files with?\n");
           String label = scan.next();
           System.out.print("What is the name of your .txt file with your barcodes?\n");
           String barcodeName = scan.next();
           System.out.print("how many barcodes do you have?\n");
       int numBarcode = scan.nextInt();
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
        String[] barcodeList;
        barcodeList = new String[numBarcode];
        File barFile = new File(barcodeName);
        Scanner scBar = new Scanner (barFile);
          for(int i=0; i<numBarcode; i++)
          {
            barcodeList[i] = scBar.nextLine();
          }
        FileReader fr1 = new FileReader(dir1 + forward);
        FileReader fr2 = new FileReader(dir1 + reverse);
        BufferedReader br1 = new BufferedReader(fr1);
        BufferedReader br2 = new BufferedReader(fr2);
        int index = 0;
        int position = 0;
        int counter=0;
        int thresh = 75;
        for (int d=0; d<numBarcode; d++)
        {
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
                if (barcoderef.equals(barcodeList[d]))
                    {
                     newseqdict.put(read, readid + "\n" + trueseq.substring(5,thresh) + "\n" + "+" + "\n" + qual + "\n");
                    }
                }
            }
           counter = counter + 1;
        }
FileWriter f0 = new FileWriter(dir1 + label + barcodeList[d] + "_nodupe_trimmed.fastq");
Set entries = newseqdict.entrySet();
Iterator iterator = entries.iterator();
while(iterator.hasNext()) {
    Map.Entry entry = (Map.Entry) iterator.next();
    String fastq = (String)entry.getValue();
    f0.write(fastq);
}
f0.close();
}
fr1.close();
fr2.close();
}
}
