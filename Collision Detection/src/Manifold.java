
public class Manifold
{
	Shape A;
	Shape B;
	float penetration;
	Vector normal;
	
	public Manifold (Shape A, Shape B){
		this.A = A;
		this.B = B;
	}
	
	public Shape getA(){
		return A;
	}
	
	public void setA(Shape a){
		this.A = a;
	}
	
	public Shape getB(){
		return B;
	}
	
	public void setB(Shape b){
		this.B = b;
	}

	public void setPenetration(float p)
	{
		this.penetration = p;
	}
	
	public float getPenetration(){
		return this.penetration;
	}
	
	public void setNormal(Vector normal){
		this.normal = normal;
	}
	
	public Vector getNormal(){
		return this.normal;
	}
	
	private float min(double restitution, double restitution2)
	{
		if (restitution > restitution2){
			return (float)restitution2;
		}
		else {
			return (float)restitution;
		}
	}
	
	void ResolveCollision( Shape a, Shape b )
	{//cast to circles
	  Circle A = (Circle)a;	
	  Circle B = (Circle)b;	
	  
	  // Calculate relative velocity
	  Vector rv = B.getVelocity().minus(A.getVelocity());
	 
	  // Calculate relative velocity in terms of the normal direction
	  float velAlongNormal = (float) rv.dot(this.getNormal());
	 
	  // Do not resolve if velocities are separating
	  if(velAlongNormal > 0){
	    return;
	  }
	 
	  // Calculate restitution
	  float e = min( A.restitution, B.restitution);
	 
	  // Calculate impulse scalar
	  float j = -(1 + e) * velAlongNormal;
	  j /= 1 / A.mass + 1 / B.mass;
	 
	  // Apply impulse
	  Vector impulse = this.getNormal().times(j);
	  A.setVelocity(A.getVelocity().minus( impulse.times( A.getInv_mass() ) ) );
	  B.setVelocity(B.getVelocity().plus( impulse.times( B.getInv_mass() ) ) );
	  //^^ ==  B.velocity += 1 / B.mass * impulse;
	}
	
}
