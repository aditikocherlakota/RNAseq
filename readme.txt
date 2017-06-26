1.  bcl2fastq
2.  gunzip all fastq files 
3.  cat fastq files from different lanes together (use run_cat_barcode)
4.  run java code to parse separate barcodes:  OutputUnique.java
4a. will need to edit source code to match to directory of files and prefix (lines 13,25,26,70)
4b. recompile:  javac OutputUnique.java
4c. run for each Celseq barcode used:  command is java OutputUnique AGACTC (each will take about 2 minutes)
