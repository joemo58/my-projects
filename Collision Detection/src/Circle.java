
public class Circle extends Shape
{
	double radius;
	Vector position;
	double speed;
	Vector velocity;
	Vector direction;
	Vector normal;			//collision normal-may need this in a collision class instead
	double penetration;			//penetration depth - also needed in collision class
	double restitution = 5;		//for collisions
	double mass = 1;
	double inv_mass = 1/mass;
	
	public Circle(double radius, Vector position, double speed){
		this.radius = radius;
		this.position = position;
		this.speed = speed;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius(double radius){
		this.radius =  radius;
	}
	
	
	public double getX() {
		return position.getX();
	}
	
	public double getY() {
		return position.getY();
	}
	
	public Vector getPosition(){
		return position;
	}
	
	public void setPosition(Vector position){
		this.position = position;
	}
	
	public Vector getDirection(){
		return this.direction;
	}
	
	public void setDirection (Vector direction){
		this.direction = direction;
	}

	
	public double getSpeed(){
		return speed;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public Vector getVelocity(){
		
		Vector n_velocity = this.getDirection().times(this.getSpeed());
		//System.out.println("velocity= " + n_velocity);
		setVelocity(n_velocity);
		return n_velocity;
	}
	
	public void setVelocity(Vector velocity){
		this.velocity = velocity;
	}
	
	public double getInv_mass(){
		return this.inv_mass;
	}
}
