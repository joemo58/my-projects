
public class AABB extends Shape
{
	 private Vector min;					//bottom left
	 private Vector max;					//top left
	
	public Vector getMax()
	{
		return max;
	}
	public void setMax(Vector max)
	{
		this.max = max;
	}
	public Vector getMin()
	{
		return min;
	}
	public void setMin(Vector min)
	{
		this.min = min;
	}
	 
}

