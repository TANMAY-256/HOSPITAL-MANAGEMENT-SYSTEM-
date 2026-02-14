import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract base class for all persons
abstract class Person {
    protected int id;
    protected String name;
    protected int age;
    protected String gender;
    protected String contact;

    public Person(int id, String name, int age, String gender, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
    }

    // Getters and setters (omitted for brevity, but can be added)
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age +
               ", Gender: " + gender + ", Contact: " + contact;
    }
}

// Patient class
class Patient extends Person {
    private String disease;
    private boolean admitted;
    private LocalDate admissionDate;

    public Patient(int id, String name, int age, String gender, String contact,
                   String disease, boolean admitted, LocalDate admissionDate) {
        super(id, name, age, gender, contact);
        this.disease = disease;
        this.admitted = admitted;
        this.admissionDate = admissionDate;
    }

    // Getters and setters
    public String getDisease() { return disease; }
    public boolean isAdmitted() { return admitted; }
    public LocalDate getAdmissionDate() { return admissionDate; }

    public void setAdmitted(boolean admitted) { this.admitted = admitted; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    @Override
    public String toString() {
        return super.toString() + ", Disease: " + disease +
               ", Admitted: " + (admitted ? "Yes" : "No") +
               (admissionDate != null ? ", Admission Date: " + admissionDate : "");
    }
}

// Doctor class
class Doctor extends Person {
    private String specialization;
    private boolean available;

    public Doctor(int id, String name, int age, String gender, String contact,
                  String specialization, boolean available) {
        super(id, name, age, gender, contact);
        this.specialization = specialization;
        this.available = available;
    }

    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return super.toString() + ", Specialization: " + specialization +
               ", Available: " + (available ? "Yes" : "No");
    }
}

// Appointment class
class Appointment {
    private static int counter = 1;
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private String time;        // e.g., "10:30 AM"
    private String status;      // Scheduled, Completed, Cancelled

    public Appointment(Patient patient, Doctor doctor, LocalDate date, String time) {
        this.appointmentId = counter++;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = "Scheduled";
    }

    // Getters
    public int getAppointmentId() { return appointmentId; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId +
               ", Patient: " + patient.getName() +
               ", Doctor: " + doctor.getName() +
               ", Date: " + date + ", Time: " + time +
               ", Status: " + status;
    }
}

// Bill class
class Bill {
    private static int counter = 1000;
    private int billId;
    private Patient patient;
    private double amount;
    private LocalDate date;
    private boolean paid;

    public Bill(Patient patient, double amount, LocalDate date) {
        this.billId = counter++;
        this.patient = patient;
        this.amount = amount;
        this.date = date;
        this.paid = false;
    }

    public int getBillId() { return billId; }
    public Patient getPatient() { return patient; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public boolean isPaid() { return paid; }

    public void setPaid(boolean paid) { this.paid = paid; }

    @Override
    public String toString() {
        return "Bill ID: " + billId + ", Patient: " + patient.getName() +
               ", Amount: $" + amount + ", Date: " + date +
               ", Paid: " + (paid ? "Yes" : "No");
    }
}

// Main system class
public class HospitalManagementSystem {
    private static List<Patient> patients = new ArrayList<>();
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Appointment> appointments = new ArrayList<>();
    private static List<Bill> bills = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int patientIdCounter = 1;
    private static int doctorIdCounter = 1;

    public static void main(String[] args) {
        // Pre-populate with some sample data for testing
        initializeData();

        int choice;
        do {
            printMenu();
            System.out.print("Enter your choice: ");
            choice = getIntInput();
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    addDoctor();
                    break;
                case 3:
                    scheduleAppointment();
                    break;
                case 4:
                    viewAllPatients();
                    break;
                case 5:
                    viewAllDoctors();
                    break;
                case 6:
                    viewAppointmentsForPatient();
                    break;
                case 7:
                    generateBill();
                    break;
                case 8:
                    viewAllBills();
                    break;
                case 9:
                    markBillAsPaid();
                    break;
                case 10:
                    dischargePatient();
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Display menu
    private static void printMenu() {
        System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Patient");
        System.out.println("2. Add Doctor");
        System.out.println("3. Schedule Appointment");
        System.out.println("4. View All Patients");
        System.out.println("5. View All Doctors");
        System.out.println("6. View Appointments for a Patient");
        System.out.println("7. Generate Bill");
        System.out.println("8. View All Bills");
        System.out.println("9. Mark Bill as Paid");
        System.out.println("10. Discharge Patient");
        System.out.println("0. Exit");
    }

    // Helper to read integer with validation
    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return num;
    }

    // Helper to read double with validation
    private static double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        double num = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return num;
    }

    // Add a new patient
    private static void addPatient() {
        System.out.println("\n--- Add Patient ---");
        int id = patientIdCounter++;
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = getIntInput();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Disease: ");
        String disease = scanner.nextLine();
        System.out.print("Is admitted? (yes/no): ");
        String admitInput = scanner.nextLine();
        boolean admitted = admitInput.equalsIgnoreCase("yes");
        LocalDate admissionDate = null;
        if (admitted) {
            System.out.print("Admission date (yyyy-mm-dd): ");
            String dateStr = scanner.nextLine();
            try {
                admissionDate = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Setting admission date to today.");
                admissionDate = LocalDate.now();
            }
        }
        Patient patient = new Patient(id, name, age, gender, contact, disease, admitted, admissionDate);
        patients.add(patient);
        System.out.println("Patient added successfully. ID: " + id);
    }

    // Add a new doctor
    private static void addDoctor() {
        System.out.println("\n--- Add Doctor ---");
        int id = doctorIdCounter++;
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = getIntInput();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Is available? (yes/no): ");
        String availInput = scanner.nextLine();
        boolean available = availInput.equalsIgnoreCase("yes");
        Doctor doctor = new Doctor(id, name, age, gender, contact, specialization, available);
        doctors.add(doctor);
        System.out.println("Doctor added successfully. ID: " + id);
    }

    // Schedule an appointment
    private static void scheduleAppointment() {
        System.out.println("\n--- Schedule Appointment ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered. Please add a patient first.");
            return;
        }
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered. Please add a doctor first.");
            return;
        }

        // Select patient
        System.out.println("Select patient by ID:");
        for (Patient p : patients) {
            System.out.println(p.getId() + ": " + p.getName());
        }
        int patientId = getIntInput();
        Patient selectedPatient = findPatientById(patientId);
        if (selectedPatient == null) {
            System.out.println("Patient not found.");
            return;
        }

        // Select doctor
        System.out.println("Select doctor by ID:");
        for (Doctor d : doctors) {
            System.out.println(d.getId() + ": " + d.getName() + " (" + d.getSpecialization() + ")");
        }
        int doctorId = getIntInput();
        Doctor selectedDoctor = findDoctorById(doctorId);
        if (selectedDoctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        // Check doctor availability
        if (!selectedDoctor.isAvailable()) {
            System.out.println("Doctor is not available. Appointment cannot be scheduled.");
            return;
        }

        System.out.print("Appointment date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Using current date.");
            date = LocalDate.now();
        }
        System.out.print("Appointment time (e.g., 10:30 AM): ");
        String time = scanner.nextLine();

        Appointment apt = new Appointment(selectedPatient, selectedDoctor, date, time);
        appointments.add(apt);
        System.out.println("Appointment scheduled successfully. ID: " + apt.getAppointmentId());
    }

    // View all patients
    private static void viewAllPatients() {
        System.out.println("\n--- List of Patients ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
        } else {
            for (Patient p : patients) {
                System.out.println(p);
            }
        }
    }

    // View all doctors
    private static void viewAllDoctors() {
        System.out.println("\n--- List of Doctors ---");
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
        } else {
            for (Doctor d : doctors) {
                System.out.println(d);
            }
        }
    }

    // View appointments for a specific patient
    private static void viewAppointmentsForPatient() {
        System.out.println("\n--- View Appointments for Patient ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("Select patient by ID:");
        for (Patient p : patients) {
            System.out.println(p.getId() + ": " + p.getName());
        }
        int patientId = getIntInput();
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        boolean found = false;
        for (Appointment a : appointments) {
            if (a.getPatient().getId() == patientId) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found for this patient.");
        }
    }

    // Generate a bill for a patient
    private static void generateBill() {
        System.out.println("\n--- Generate Bill ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("Select patient by ID:");
        for (Patient p : patients) {
            System.out.println(p.getId() + ": " + p.getName());
        }
        int patientId = getIntInput();
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.print("Bill amount: $");
        double amount = getDoubleInput();
        LocalDate date = LocalDate.now();
        Bill bill = new Bill(patient, amount, date);
        bills.add(bill);
        System.out.println("Bill generated successfully. Bill ID: " + bill.getBillId());
    }

    // View all bills
    private static void viewAllBills() {
        System.out.println("\n--- List of Bills ---");
        if (bills.isEmpty()) {
            System.out.println("No bills generated.");
        } else {
            for (Bill b : bills) {
                System.out.println(b);
            }
        }
    }

    // Mark a bill as paid
    private static void markBillAsPaid() {
        System.out.println("\n--- Mark Bill as Paid ---");
        if (bills.isEmpty()) {
            System.out.println("No bills to mark.");
            return;
        }
        System.out.print("Enter Bill ID: ");
        int billId = getIntInput();
        Bill bill = findBillById(billId);
        if (bill == null) {
            System.out.println("Bill not found.");
            return;
        }
        if (bill.isPaid()) {
            System.out.println("Bill is already marked as paid.");
        } else {
            bill.setPaid(true);
            System.out.println("Bill marked as paid.");
        }
    }

    // Discharge a patient (set admitted to false)
    private static void dischargePatient() {
        System.out.println("\n--- Discharge Patient ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("Select patient by ID:");
        for (Patient p : patients) {
            if (p.isAdmitted()) {
                System.out.println(p.getId() + ": " + p.getName());
            }
        }
        int patientId = getIntInput();
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        if (!patient.isAdmitted()) {
            System.out.println("Patient is not currently admitted.");
            return;
        }
        patient.setAdmitted(false);
        patient.setAdmissionDate(null);  // optional
        System.out.println("Patient discharged successfully.");
    }

    // Helper methods to find objects by ID
    private static Patient findPatientById(int id) {
        for (Patient p : patients) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private static Doctor findDoctorById(int id) {
        for (Doctor d : doctors) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    private static Bill findBillById(int id) {
        for (Bill b : bills) {
            if (b.getBillId() == id) return b;
        }
        return null;
    }

    // Initialize with some sample data
    private static void initializeData() {
        // Add a couple of doctors
        doctors.add(new Doctor(doctorIdCounter++, "Dr. Smith", 45, "Male", "555-1234", "Cardiology", true));
        doctors.add(new Doctor(doctorIdCounter++, "Dr. Adams", 38, "Female", "555-5678", "Pediatrics", true));
        doctors.add(new Doctor(doctorIdCounter++, "Dr. Lee", 50, "Male", "555-9012", "Orthopedics", false));

        // Add a couple of patients
        patients.add(new Patient(patientIdCounter++, "John Doe", 30, "Male", "555-1111", "Flu", false, null));
        patients.add(new Patient(patientIdCounter++, "Jane Roe", 25, "Female", "555-2222", "Fracture", true, LocalDate.now().minusDays(2)));

        // Add a sample appointment
        Appointment apt = new Appointment(patients.get(0), doctors.get(0), LocalDate.now().plusDays(1), "09:30 AM");
        appointments.add(apt);

        // Add a sample bill
        Bill bill = new Bill(patients.get(0), 150.0, LocalDate.now());
        bills.add(bill);
    }
}