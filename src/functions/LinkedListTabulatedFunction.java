package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction
{
	private FunctionNode head;
	private int size;

	public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount)
	{
		if (leftX >= rightX || pointsCount < 2)
		{
			throw new IllegalArgumentException("Некорректные параметры конструктора!");
		}

		double step = (rightX - leftX) / (pointsCount - 1);

		this.head = new FunctionNode(new FunctionPoint(leftX, 0));
		FunctionNode currentNode = head;

		for (int i = 1; i < pointsCount; i++)
		{
			double x = leftX + i * step;
			FunctionPoint point = new FunctionPoint(x, 0);
			FunctionNode newNode = new FunctionNode(point);

			currentNode.setNext(newNode);
			newNode.setPrevious(currentNode);
			currentNode = newNode;
		}

		head.setPrevious(currentNode);
		currentNode.setNext(head);
		size = pointsCount;
	}

	public LinkedListTabulatedFunction(double leftX, double rightX, double[] values)
	{
		if (leftX >= rightX || values.length < 2)
		{
			throw new IllegalArgumentException("Некорректные параметры конструктора!");
		}

		double step = (rightX - leftX) / (values.length - 1);

		this.head = new FunctionNode(new FunctionPoint(leftX, values[0]));
		FunctionNode currentNode = head;

		for (int i = 1; i < values.length; i++)
		{
			double x = leftX + i * step;
			FunctionPoint point = new FunctionPoint(x, values[i]);
			FunctionNode newNode = new FunctionNode(point);

			currentNode.setNext(newNode);
			newNode.setPrevious(currentNode);
			currentNode = newNode;
		}

		head.setPrevious(currentNode);
		currentNode.setNext(head);
		size = values.length;

	}

	private void checkIndex(int index)
	{
		if (index < 0 || index >= size)
		{
			throw new FunctionPointIndexOutOfBoundsException();
		}
	}
	@Override
	public double getLeftDomainBorder()
	{
		return head.getPoint().GetX();
	}

	@Override
	public double getRightDomainBorder()
	{
		return head.getPrevious().getPoint().GetX();
	}

	@Override
	public double getFunctionValue(double x)
	{
		if (x < getLeftDomainBorder() || x > getRightDomainBorder())
		{
			throw new FunctionPointIndexOutOfBoundsException();
		}

		FunctionNode node = null;
		FunctionNode node2 = head;
		for (int i = 0; i < size; i++)
		{
			if (node2.getPoint().GetX() == x)
			{
				node = node2;
			}
			node2 = node2.getNext();
		}

		double x0 = node.getPoint().GetX();
		double y0 = node.getPoint().GetY();

		if (x == x0)
		{
			return y0;
		}

		FunctionNode prev = node.getPrevious();
		double x1 = prev.getPoint().GetX();
		double y1 = prev.getPoint().GetY();

		return y1 + ((y0 - y1) / (x0 - x1)) * (x - x1);
	}

	@Override
	public int getPointsCount()
	{
		return size;
	}

	@Override
	public FunctionPoint getPoint(int index)
	{
		checkIndex(index);
		FunctionNode node = getNodeByIndex(index);
		return node.getPoint();
	}

	@Override
	public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException
	{
		checkIndex(index);

		if (index == 0 && point.GetX() >= getPointX(1))
		{
			throw new InappropriateFunctionPointException();
		}
		if (index == size - 1 && point.GetX() <= getPointX(size - 2))
		{
			throw new InappropriateFunctionPointException();
		}
		if (index > 0 && point.GetX() <= getPointX(index - 1))
		{
			throw new InappropriateFunctionPointException();
		}
		if (index < size - 1 && point.GetX() >= getPointX(index + 1))
		{
			throw new InappropriateFunctionPointException();
		}

		FunctionNode node = getNodeByIndex(index);
		node.getPoint().SetX(point.GetX());
		node.getPoint().SetY(point.GetY());

	}

	@Override
	public double getPointX(int index)
	{
		checkIndex(index);
		FunctionNode node = getNodeByIndex(index);
		return node.getPoint().GetX();
	}

	@Override
	public void setPointX(int index, double x) throws InappropriateFunctionPointException
	{
		checkIndex(index);

		if (index == 0 && x >= getPointX(1))
		{
			throw new InappropriateFunctionPointException();
		}
		if (index == size - 1 && x <= getPointX(size - 2))
		{
			throw new InappropriateFunctionPointException();
		}
		if (index > 0 && x <= getPointX(index - 1))
		{
			throw new InappropriateFunctionPointException();
		}
		if (index < size - 1 && x >= getPointX(index + 1))
		{
			throw new InappropriateFunctionPointException();
		}

		FunctionNode node = getNodeByIndex(index);
		node.getPoint().SetX(x);

	}

	@Override
	public double getPointY(int index)
	{
		checkIndex(index);
		FunctionNode node = getNodeByIndex(index);
		return node.getPoint().GetY();
	}

	@Override
	public void setPointY(int index, double y)
	{
		checkIndex(index);
		FunctionNode node = getNodeByIndex(index);
		node.getPoint().SetY(y);
	}

	@Override
	public void deletePoint(int index)
	{
		checkIndex(index);

		if (size < 3)
		{
			throw new IllegalStateException("Невозможно удалить точку, так как количество точек менее трех!");
		}

		if (index == 0)
		{
			head = head.getNext();
		}

		FunctionNode node = getNodeByIndex(index);
		FunctionNode prev = node.getPrevious();
		FunctionNode next = node.getNext();

		prev.setNext(next);
		next.setPrevious(prev);

		size--;

	}

	@Override
	public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException
	{
		if (point.GetX() < getLeftDomainBorder() || point.GetX() > getRightDomainBorder())
		{
			throw new InappropriateFunctionPointException();
		}

		FunctionNode newNode = new FunctionNode(point);
		if (head == null)
		{
			head = newNode;
			head.setNext(head);
			head.setPrevious(head);
		}
		else
		{
			FunctionNode current = head;
			while (current.getNext() != head && current.getNext().getPoint().GetX() < point.GetX())
			{
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			newNode.setPrevious(current);
			current.setNext(newNode);
			newNode.getNext().setPrevious(newNode);
		}
		size++;
	}

	private FunctionNode getNodeByIndex(int index) // возвращает ссылку на объект элемента списка по его номеру
	{
		checkIndex(index);
		FunctionNode current = head;
		for (int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		return current;
	}

	private FunctionNode addNodeToTail() // добавляет элемент в конец списка и возвращает ссылку на объект
	{
		FunctionNode newNode = new FunctionNode(new FunctionPoint());
		if (size == 0)
		{
			head = newNode;
			head.setNext(head);
			head.setPrevious(head);
		}
		else
		{
			FunctionNode tail = head.getPrevious();
			newNode.setPrevious(tail);
			newNode.setNext(head);
			tail.setNext(newNode);
			head.setPrevious(newNode);
		}
		size++;
		return newNode;
	}

	private FunctionNode addNodeByIndex(int index) // добавляет новый элемент в указанную позицию списка
	{
		checkIndex(index);
		if (index == size)
		{
			return addNodeToTail();
		}
		FunctionNode newNode = new FunctionNode(new FunctionPoint());
		if (index == 0)
		{
			FunctionNode tail = head.getPrevious();
			newNode.setNext(head);
			newNode.setPrevious(tail);
			tail.setNext(newNode);
			head.setPrevious(newNode);
			head = newNode;
		}
		else
		{
			FunctionNode current = getNodeByIndex(index - 1);
			newNode.setNext(current.getNext());
			newNode.setPrevious(current);
			current.setNext(newNode);
			newNode.getNext().setPrevious(newNode);
		}
		size++;
		return newNode;
	}

	private FunctionNode deleteNodeByIndex(int index) // удаляет элемент по индексу и возвращает ссылку на объект
	{
		checkIndex(index);
		if (size == 1) {
			FunctionNode deleted = head;
			head = null;
			size--;
			return deleted;
		}
		FunctionNode current = getNodeByIndex(index);
		current.getPrevious().setNext(current.getNext());
		current.getNext().setPrevious(current.getPrevious());
		if (index == 0)
		{
			head = current.getNext();
		}
		size--;
		return current;
	}

}