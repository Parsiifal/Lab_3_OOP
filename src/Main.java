import functions.*;

public class Main
{
	public static void main(String[] args) throws InappropriateFunctionPointException
	{
		double[] values = new double[]{1, 2, 4, 5, 5.34, 7.42};
		TabulatedFunction a = new LinkedListTabulatedFunction(-7, 8, values);

		FunctionPoint temp = new FunctionPoint(6, 3);

		a.addPoint(temp);

		printFunctionValues(a);

		FunctionPoint temp1 = new FunctionPoint(-0.5, 3);

		a.setPoint(3, temp1);

		printFunctionValues(a);

	}

	public static void printFunctionValues(TabulatedFunction function) // функция для вывода значений функции
	{
		System.out.println("Значения функции:");

		for (int i = 0; i < function.getPointsCount(); i++)
		{
			double x = function.getPointX(i);
			double y = function.getPointY(i);
			System.out.println("f(" + x + ") = " + y);
		}
		System.out.println();
	}
}
