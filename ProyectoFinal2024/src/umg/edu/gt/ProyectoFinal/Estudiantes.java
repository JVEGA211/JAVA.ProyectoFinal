package umg.edu.gt.ProyectoFinal;

public class Estudiantes {
    private String ID;
    private String Nombre;
    private String Apellido;
    private String Phone;
    private String Email;

    public Estudiantes(String ID, String Nombre, String Apellido, String Phone, String Email) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Phone = Phone;
        this.Email = Email;
    }

    public static Estudiantes fromString(String studentData) {
        String[] parts = studentData.split(",");
        return new Estudiantes(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public String GetID() {
        return ID;
    }

    public String GetApellido() {
        return Apellido;
    }

    @Override
    public String toString() {
        return ID + "," + Nombre + "," + Apellido + "," + Phone + "," + Email;
    }
}
