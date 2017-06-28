import java.io.*;
import java.util.*;
import java.util.Scanner;

class OutputUnique {
    public static void main(String args[]) throws Exception {
        String barcode="";
        for (String x: args) {
            String delimiter = " ";
            String[] temp = x.split(delimiter);
            barcode = temp[0];
            }


        Scanner scan= new Scanner(System.in);
        System.out.print("what is the directory to your run folder?\n");
        String dir1 = scan.next();
        System.out.print("ensure that you have a two .txt files: \n one for your front end reads in order and one for your reverse ends in the same order. \n what is the name of your front read text file?\n");
        String textForward = scan.next();
        System.out.print("what is the name of your reverse text file\n");
        String textReverse = scan.next();
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
        String[] forward;
        String[] reverse;
        forward = new String[numBarcode];
        reverse = new String[numBarcode];
        File forwardSeq = new File(textForward);
        File reverseSeq = new File(textReverse);
        Scanner scFor = new Scanner (forwardSeq);
        Scanner scRev = new Scanner (reverseSeq);
          for(int i=0; i<numBarcode; i++)
          {
            forward[i] = scFor.nextLine();
            reverse[i] = scRev.nextLine();
          }
          for (int x=0; x<numBarcode; x++){
        FileReader fr1 = new FileReader(dir1 + forward[x]);
        FileReader fr2 = new FileReader(dir1 + reverse[x]);
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
FileWriter f0 = new FileWriter(dir1 + forward[x] + barcode + "_nodupe_trimmed.fastq");
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
