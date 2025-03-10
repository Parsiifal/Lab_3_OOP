package functions;

public class FunctionPoint
{
    private double x;
    private double y;

    public FunctionPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point)
    {
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint()
    {
        this.x = 0;
        this.y = 0;
    }

    public double GetX()
    {
        return x;
    }

    public void SetX(double x)
    {
        this.x = x;
    }
    public double GetY()
    {
        return y;
    }

    public void SetY(double y)
    {
        this.y = y;
    }

}