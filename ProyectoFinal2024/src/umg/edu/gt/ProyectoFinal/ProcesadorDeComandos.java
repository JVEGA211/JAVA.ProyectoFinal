package umg.edu.gt.ProyectoFinal;

import java.io.*;

public class ProcesadorDeComandos {
    private DBArbol IdIndex;
    private DBArbol ApellidoIndex;
    private RandomAccessFile dataFile;

    public ProcesadorDeComandos(DBArbol IdIndex, DBArbol ApellidoIndex, String DataFilePath) throws IOException {
        this.IdIndex = IdIndex;
        this.ApellidoIndex = ApellidoIndex;
        this.dataFile = new RandomAccessFile(DataFilePath, "rw");
    }

    public void ProcessCommands(String CommandFilePath, String OutputFilePath) throws IOException {
        BufferedReader Reader = new BufferedReader(new FileReader(CommandFilePath));
        BufferedWriter Writer = new BufferedWriter(new FileWriter(OutputFilePath));
        String line;

        while ((line = Reader.readLine()) != null) {
            String[] parts = line.split(" ");
            String Command = parts[0];
            switch (Command) {
                case "Encontrar":
                    if (parts[1].equals("ID")) {
                        findById(parts[2], Writer);
                    } else if (parts[1].equals("Apellido")) {
                        findByApellido(parts[2], Writer);
                    }
                    break;
                case "Agregar":
                    String studentData = line.substring(8); // Ignorar "Agregar "
                    addStudent(studentData, Writer);
                    break;
                case "Listar":
                    if (parts[2].equals("ID")) {
                        listById(Writer);
                    } else if (parts[2].equals("Apellido")) {
                        listByApellido(Writer);
                    }
                    break;
                default:
                    Writer.write("Comando no reconocido: " + Command + "\n");
                    break;
            }
        }
        Reader.close();
        Writer.close();
    }

    private void findById(String ID, BufferedWriter Writer) throws IOException {
        DBNodo node = IdIndex.search(ID);
        if (node == null) {
            Writer.write("NO ENCONTRADO\n");
        } else {
            for (String key : node.GetKeys()) {
                if (key.equals(ID)) {
                    long pos = Long.parseLong(key.split(",")[1]);
                    dataFile.seek(pos);
                    String record = dataFile.readLine();
                    Writer.write(record + "\n");
                    return;
                }
            }
            Writer.write("NO ENCONTRADO\n");
        }
    }

    private void findByApellido(String Apellido, BufferedWriter Writer) throws IOException {
        DBNodo node = ApellidoIndex.search(Apellido);
        if (node == null) {
            Writer.write("NO ENCONTRADO\n");
        } else {
            for (String key : node.GetKeys()) {
                if (key.equals(Apellido)) {
                    long pos = Long.parseLong(key.split(",")[1]);
                    dataFile.seek(pos);
                    String record = dataFile.readLine();
                    Writer.write(record + "\n");
                    return;
                }
            }
            Writer.write("NO ENCONTRADO\n");
        }
    }

    private void addStudent(String DataStudent, BufferedWriter Writer) throws IOException {
        Estudiantes record = Estudiantes.fromString(DataStudent);
        long pos = dataFile.length();  // Obtener la posición donde se va a escribir el nuevo registro
        dataFile.seek(pos);
        dataFile.writeBytes(record.toString() + "\n");

        IdIndex.insert(record.GetID() + "," + pos);
        ApellidoIndex.insert(record.GetApellido() + "," + pos);

        Writer.write("ESTUDIANTE AGREGADO: " + record.toString() + "\n");
    }

    private void listById(BufferedWriter Writer) throws IOException {
        listBy(Writer, IdIndex);
    }

    private void listByApellido(BufferedWriter Writer) throws IOException {
        listBy(Writer, ApellidoIndex);
    }

    private void listBy(BufferedWriter Writer, DBArbol Index) throws IOException {
        listNodes(Writer, Index.getRoot());
    }

    private void listNodes(BufferedWriter Writer, DBNodo Nodo) throws IOException {
        if (Nodo == null) {
            return;
        }
        for (String key : Nodo.GetKeys()) {
            long pos = Long.parseLong(key.split(",")[1]);
            dataFile.seek(pos);
            String record = dataFile.readLine();
            Writer.write(record + "\n");
        }
        for (DBNodo child : Nodo.GetChildren()) {
            listNodes(Writer, child);
        }
    }
}

