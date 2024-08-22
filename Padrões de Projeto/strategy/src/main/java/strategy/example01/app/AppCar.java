package strategy.example01.app;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import decorator.stream.csv.CSVInputStream;
import strategy.example01.model.Car;
import strategy.example01.reader.CSVCarReader;
import strategy.example01.reader.CarReader;
import strategy.example01.stock.CarStock;
import strategy.example01.stock.Stock;

public class AppCar 
{
	private Stock<Car> carStock;
	
	//-------------------------------------------------------------------------------------
	public AppCar (Stock<Car> carStock) throws IOException
	{
		this.carStock = carStock;
		this.carStock.load();	
	}
	
	
	//-------------------------------------------------------------------------------------
	static private
	CarReader createCarReader(String csvCarFile) throws IOException
	{
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(csvCarFile);
		assert (inputStream != null) : "File Not Found: " + csvCarFile;
	
		CSVInputStream csvInputStream   = new CSVInputStream(inputStream);
		CarReader carReader = new CSVCarReader(csvInputStream);
		
		return  carReader;		
	}
	
	//-------------------------------------------------------------------------------------

	public void printAllCars() {
		carStock.stream().forEach(System.out::println);
	}
	
	public final void printAllCarsOrderedByYear() {
		carStock.stream().sorted
		((carA, carB) -> (carA.getYear() - carB.getYear()))
		.forEach(System.out::println);
	}
	
	public final void printAllCarsOrderedByBrand() {
		carStock.stream().sorted().forEach(System.out::println);
	}
	
	public final void printAllCarsOrderedByLicence() {
		carStock.stream().sorted().forEach(System.out::println);
	}
	
	public final void printAllCarsByBrand(String brand) {
		
		System.out.println("\nCarros da marca " + brand);

		carStock.stream()
				.filter(car -> brand.equalsIgnoreCase(car.getBrand()))
				.forEach(car -> System.out.println(car));
		}
	
	public final void printAllOldCars(int year) {
		int currentYear = Year.now().getValue() - year;
		
		System.out.println("\nCarros com mais de " + year + " anos");

		carStock.stream()
				.filter(car -> currentYear > car.getYear())
				.forEach(car -> System.out.println(car));
	}
	
	public final void printAllNewerCars(int year) {
		int currentYear = Year.now().getValue() - year;
		
		System.out.println("\nCarros com menos de " + year + " anos");

		carStock.stream()
				.filter(car -> currentYear < car.getYear())
				.forEach(car -> System.out.println(car));
	}
	
	public final void printAllOldCarsInAscendingOrder(int year) {
		int currentYear = Year.now().getValue() - year;
		
		System.out.println("\nCarros com mais de " + year + " anos em ordem crescente");
		
		carStock.stream()
				.filter(car -> currentYear > car.getYear())
				.sorted(((carA, carB) -> (carA.getYear() - carB.getYear())))
				.forEach(car -> System.out.println(car));
	}
	
	public final void printAllNewerCarsInDescendingOrder(int year) {
		int currentYear = Year.now().getValue() - year;
		
		System.out.println("\nCarros com menos de " + year + " anos em ordem decrescente");
		
		carStock.stream()
				.filter(car -> currentYear < car.getYear())
				.sorted(((carA, carB) -> (carA.getYear() - carB.getYear())))
				.forEach(car -> System.out.println(car));
	}
	
	public final void printAllOldCarsByBrandInAscendingOrder(String brand, int year) {
		int currentYear = Year.now().getValue() - year;
		
		System.out.println("\nCarros da " + brand + " com mais de " + year + " anos em ordem crescente");
		
		carStock.stream()
				.filter(car -> currentYear > car.getYear() 
						&& brand.equalsIgnoreCase(car.getBrand()))
				.sorted(((carA, carB) -> (carA.getYear() - carB.getYear())))
				.forEach(car -> System.out.println(car));
	}
	
	//-------------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException
	{
		String carsFile = "CSVCarStockData.csv";
		
		CarReader carReader = createCarReader(carsFile);
		Stock<Car> carStock = new CarStock(carReader);
		
		AppCar app = new AppCar(carStock);
		
		app.printAllCarsByBrand("Wolkswagen");
		app.printAllOldCars(10);
		app.printAllNewerCars(10);
		app.printAllOldCarsInAscendingOrder(10);
		app.printAllNewerCarsInDescendingOrder(10);
		app.printAllOldCarsByBrandInAscendingOrder("Wolkswagen", 10);
		
	}
	
}
