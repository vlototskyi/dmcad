package com.lab.five;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Izomorfizm {

    final static int ZN = 8;
    final static int ZE = 12;
    final static int N = 8;
    static boolean general = false;


    public static void main(String[] args) {
        izomorfizm();
        if(general)
            System.out.println("its work");
        else System.out.println("its bad");


    }

    public static boolean[][] swapLeafs(boolean[][] mat, int x, int y){
        for (int i=0;i<mat.length;i++){
            boolean k=mat[i][x];
            mat[i][x]=mat[i][y];
            mat[i][y]=k;
        }
        for (int j=0;j<mat.length;j++){
            boolean k=mat[x][j];
            mat[x][j]=mat[y][j];
            mat[y][j]=k;
        }
        return mat;
    }

    public static boolean ETS(boolean[][] oneGraf,boolean[][] twoGraf){

        for(int i=0;i<oneGraf.length;i++){
            for (int j=i; j<oneGraf.length;j++){
                if(oneGraf.equals(swapLeafs(twoGraf,i,j))) return true;
            }
        }

        return false;
    }

    public static void izomorfizm()
    {
        MatGraf oneInc= new MatGraf(ZN);
        MatGraf twoInc= new MatGraf(ZN);
        Eji[] oneGraf = new Eji[ZE];
        Eji[] twoGraf = new Eji[ZE];
        Eji.readGrafFromFile(oneGraf,"C:\\Users\\vlototskyi\\IdeaProjects\\dmcad\\src\\com\\lab\\fifth\\lab51");
        Eji.readGrafFromFile(twoGraf,"C:\\Users\\vlototskyi\\IdeaProjects\\dmcad\\src\\com\\lab\\fifth\\lab52");
        for(int i=0;i<ZE;i++)
        {
            oneInc.mat[oneGraf[i].A][oneGraf[i].B]=true;
            oneInc.mat[oneGraf[i].B][oneGraf[i].A]=true;
            twoInc.mat[twoGraf[i].A][twoGraf[i].B]=true;
            twoInc.mat[twoGraf[i].B][twoGraf[i].A]=true;
        }

        oneInc.print();
        twoInc.print();
        oneInc.antiflex(twoInc, N-1);
    }

    private static class MatGraf {
        public boolean[][] mat;
        int size;

        private MatGraf(int size){
            this.size=size;
            mat=new boolean[size][size];
        }

        public boolean sameAs(MatGraf da){
            if(da.size!=this.size) return false;
            for (int i=0;i<size;i++) {
                for (int j = 0; j < size; j++) {
                    if (da.mat[i][j]!=this.mat[i][j]) return false;
                }
            }
            return true;
        }

        static void reverse(MatGraf P, int m) {
            int i = 0, j = m;
            while (i < j) {
                P.swapLeafs(i,j);
                ++i;
                --j;
            }
        }

        void antiflex(MatGraf P, int m) {
            int i;
            if (m == 0) {
                if(this.sameAs(P)) {
                    general=true;
                }
            } else {
                for (i = 0; i <= m; ++i) {
                    this.antiflex(P, m-1);
                    if (i < m) {
                        P.swapLeafs(i,m);
                        reverse(P, m - 1);
                    }
                }
            }
        }

        public void print() {
            for (int i = 0; i < size; i++) {
                for (int j=0;j<size;j++){
                    System.out.print(mat[i][j]+ " ");
                }
                System.out.println();
            }
            System.out.println("---------------------------------");
        }
        public void swapLeafs(int x, int y){
            for (int i=0;i<size;i++){
                boolean k=mat[i][x];
                mat[i][x]=mat[i][y];
                mat[i][y]=k;
            }
            for (int j=0;j<size;j++){
                boolean k=mat[x][j];
                mat[x][j]=mat[y][j];
                mat[y][j]=k;
            }
        }

    }

    private static class Eji {
        public int A;
        public int B;
        private Eji(int a, int b){
            A=a;
            B=b;
        }

        public static void readGrafFromFile(Eji[] graf,String filename){
            try(FileReader reader = new FileReader(filename))
            {
                int c;
                int numbOut=0, numbIn=0, i=0;
                while((c=reader.read())!=-1){
                    if ((char)c=='\n') {
                        graf[i]=new Eji(numbOut,numbIn);
                        i++;
                    }
                    else if (c >= 65) {
                        numbOut= Transformer.upperLetterToNum((char) c);
                        while((c = reader.read())<65);
                        numbIn= Transformer.upperLetterToNum((char) c);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Transformer {
        public static char numToUpperLetter(int num) {
            return (char)(num+65);
        }
        public static int upperLetterToNum(char s) {
            return (int)s-65;
        }
    }



}
