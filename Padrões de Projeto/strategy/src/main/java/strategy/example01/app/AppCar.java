package strategy.example01.app;

import java.io.IOException;
import java.io.InputStream;
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
	
	//-------------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException
	{
		String carsFile = "CSVCarStockData.csv";
		
		CarReader carReader = createCarReader(carsFile);
		Stock<Car> carStock = new CarStock(carReader);
		
		AppCar app = new AppCar(carStock);
		
		app.printAllCarsOrderedByYear();
		
	}
	
}
