package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {
	
    private LocalDate dateJoined;
	private int monthWorkingInYear;
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	public Employee(Person person) {
		 this.employeeId = person.getEmployeeId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.idNumber = person.getIdNumber();
        this.address = person.getAddress();
		this.dateJoined = LocalDate.of(person.getYearJoined(), person.getMonthJoined(), person.getDayJoined());
		this.isForeigner = person.isForeigner();
        this.isMan = person.getIsMan();
        this.spouse = person.getSpouse();
		
		person.childNames = new LinkedList<String>();
		person.childIdNumbers = new LinkedList<String>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {	
		monthlySalary = switch (grade) {
            case 1 -> 3000000;
            case 2 -> 5000000;
            case 3 -> 7000000;
            default -> throw new IllegalArgumentException("Invalid grade: " + grade);
        };
        if (isForeigner) {
            monthlySalary *= 1.5;
        }
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public int getAnnualIncomeTax() {
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		LocalDate date = LocalDate.now();
		
		if (date.getYear() == dateJoined.getYear()) {
			monthWorkingInYear = date.getMonthValue() - dateJoined.getMonthValue();
		}else {
			monthWorkingInYear = 12;
		}
		
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
	}
}
