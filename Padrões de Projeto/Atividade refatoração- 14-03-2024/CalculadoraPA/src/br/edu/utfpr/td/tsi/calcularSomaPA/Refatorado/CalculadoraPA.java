package br.edu.utfpr.td.tsi.calcularSomaPA.Refatorado;

import java.io.PrintStream;
import java.util.Scanner;

public class CalculadoraPA {

	public static void main(String[] args) {
		int numeroTermos = leNumeroTermos();
		int primeiroTermo = lePrimeiroTermo();
		int razao = leRazao();

		int ultimoTermo = calcularUltimoTermo(numeroTermos, primeiroTermo, razao);
		int soma = calcularSomaPA(numeroTermos, primeiroTermo, ultimoTermo);

		exibirTermos(numeroTermos, primeiroTermo, razao);
		exibirSoma(soma);
	}

	public static int leNumeroTermos() {
		int numeroTermos;
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("Digite o número de termos da PA:");
			numeroTermos = scanner.nextInt();
		} while (numeroTermos < 2);

		return numeroTermos;
	}

	public static int lePrimeiroTermo() {
		int primeiroTermo;
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("Digite o primeiro termo da PA:");
			primeiroTermo = scanner.nextInt();
		} while (primeiroTermo < 1);

		return primeiroTermo;
	}

	public static int leRazao() {
		int razao;
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("Digite a razão da PA:");
			razao = scanner.nextInt();
		} while (razao < 1);

		return razao;
	}

	public static int calcularUltimoTermo(int numeroTermos, int primeiroTermo, int razao) {
		return primeiroTermo + (numeroTermos - 1) * razao;
	}

	public static int calcularSomaPA(int numeroTermos, int primeiroTermo, int ultimoTermo) {
		return ((primeiroTermo + ultimoTermo) * numeroTermos) / 2;
	}

	public static void exibirTermos(int numeroTermos, int primeiroTermo, int razao) {
		PrintStream output = System.out;

		for (int i = 1; i <= numeroTermos; i++) {
			int termoAtual = primeiroTermo + (i - 1) * razao;
			output.printf("Termo %d=%d\n", i, termoAtual);
		}
	}

	public static void exibirSoma(int soma) {
		System.out.printf("A soma é: %d\n", soma);
	}
}
