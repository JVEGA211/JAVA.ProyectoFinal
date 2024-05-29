package umg.edu.gt.ProyectoFinal;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java Main <ArchivoRegistros> <miBaseDeDatos> <ArchivoComandos> <ArchivoSalida>");
            return;
        }

        String dataFilePath = args[0];
        String commandFilePath = args.length > 2 ? args[2] : null;
        String outputFilePath = args.length > 3 ? args[3] : null;

        int t = 3; // Grado mínimo del árbol-B

        DBArbol IDIndex = new DBArbol(t);
        DBArbol ApellidoIndex = new DBArbol(t);

        try {
            ProcesadorDeComandos processor = new ProcesadorDeComandos(IDIndex, ApellidoIndex, dataFilePath);

            if (commandFilePath != null && outputFilePath != null) {
                processor.ProcessCommands(commandFilePath, outputFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}