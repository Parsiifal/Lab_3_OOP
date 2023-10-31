package functions;

public class FunctionNode
{
	private FunctionPoint point;
	private FunctionNode next;
	private FunctionNode previous;

	public FunctionNode(FunctionPoint point)
	{
		this.point = point;
		this.next = this;
		this.previous = this;
	}

	public FunctionPoint getPoint()
	{
		return point;
	}

	public FunctionNode getNext()
	{
		return next;
	}

	public FunctionNode getPrevious()
	{
		return previous;
	}

	public void setNext(FunctionNode next)
	{
		this.next = next;
	}

	public void setPrevious(FunctionNode previous)
	{
		this.previous = previous;
	}

}