import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sudoku {
    
    static int soluciones = 0;
    
    static void calcularSudoku(int fila, int columna, int caja, int[][][] sudoku, boolean[][][] casilla) throws InterruptedException {
        boolean llamada = false;
        int k = caja;
        int i = fila;
        int j = columna;
        while(i < 3 && j < 3 && k < 9) {
            if(i == 2 && j == 2 && k == 8 && sudoku[2][2][8] != 0) {
                soluciones++;
                System.out.println("####SOLUTION Nº"+soluciones+"####");
                print(sudoku);
                System.out.println();
                return;
            }
            if(casilla[i][j][k]) {
                int filaAux = i;
                int columnaAux = j;
                int cajaAux = k;
                for(int valor  = 1; valor <= 9; valor++) {
                    if(comprobarFila(sudoku, i, k, valor) && comprobarColumna(sudoku, j, k, valor)
                            && comprobarCaja(sudoku, k, valor)) {
                        sudoku[i][j][k] = valor;
                        llamada = true;
                        casilla[i][j][k] = false;
                        if(j < 2) columnaAux++;
                        else if(i < 2 && j == 2) {
                            filaAux++;
                            columnaAux = 0;
                        }
                        else if(i == 2 && j == 2 && k < 8) {
                            cajaAux++;
                            filaAux = 0;
                            columnaAux = 0;
                        }
                        calcularSudoku(filaAux, columnaAux, cajaAux, sudoku, casilla);
                        casilla[i][j][k] = true;
                        llamada = false;
                        sudoku[i][j][k] = 0;
                        filaAux = i;
                        columnaAux = j;
                        cajaAux = k;
                    }
                }
                if (llamada == false) return;
            }
            else {
                if(j < 2) j++;
                else if(i < 2 && j == 2) {
                    i++;
                    j = 0;
                }
                else if(i == 2 && j == 2 && k < 8) {
                    k++;
                    i = 0;
                    j = 0;
                }
            }
        }
    }
    
    static boolean comprobarFila(int[][][] sudoku, int i, int k, int numero) {
        int min = 3*(k/3);
        int max = 3+(3*(k/3));
        for(k = min; k < max; k++) {
            for(int j = 0; j < 3; j++) {
                if(sudoku[i][j][k] == numero) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static boolean comprobarColumna(int[][][] sudoku, int j, int k, int numero) {
        int mod = k%3;
        for(k = mod; k <= 6+mod; k+=3) {
            for(int i = 0; i < 3; i++) {
                if(sudoku[i][j][k] == numero) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static boolean comprobarCaja(int[][][] sudoku, int k, int numero) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(sudoku[i][j][k] == numero) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static int[][][] formarSudoku(int[][] m) {
        int[][][] sudoku = new int[3][3][9];
        
        for(int k = 0; k < 9; k++) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    sudoku[i][j][k] = m[i+(3*(k/3))][j+(k-(3*(k/3)))*3];
                }
            }
        }
        
        return sudoku;
    }
    
    static boolean[][][] comprobarCasillas(int[][][] s) {
        boolean[][][] casillas = new boolean[3][3][9];
        for(int k = 0; k < 9; k++) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    casillas[i][j][k] = (s[i][j][k] == 0);
                }
            }
        }
        
        return casillas;
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        InputStreamReader leer = new InputStreamReader(System.in);
        BufferedReader teclado = new BufferedReader(leer);
        String linea;
        String[] inValues;
        int[][][] sudoku = new int[3][3][9];
        boolean[][][] casillas = new boolean[3][3][9];
        int[][] matrizLectura = new int[9][9];
        
        for (int i = 0; i < 9; i++) {
            linea = teclado.readLine();
            inValues = linea.split(" ");
            while(inValues.length != 9) {
                linea = teclado.readLine();
                inValues = linea.split(" ");
            }
            for (int j = 0; j < 9; j++) {
                matrizLectura[i][j] = Integer.parseInt(inValues[j]);
            }
        }
        
        sudoku = formarSudoku(matrizLectura);
        casillas = comprobarCasillas(sudoku);
        calcularSudoku(0, 0, 0, sudoku, casillas);
        System.out.println("##Nº of Solutions: "+soluciones+" ##");
    }
    
    static void print(int[][][] sudoku){
        for(int k = 0; k<9; k+=3){
            for(int i = 0; i<3; i++){
                printRow(sudoku,i,k);
                System.out.println();
            }
        }
    }
    
    static void printRow(int[][][] sudoku, int i, int k){
        int min = 3*(k/3);
        int max = 3+(3*(k/3));
        for(k = min; k < max; k++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(sudoku[i][j][k]+" ");
            }
        }
    }
}
