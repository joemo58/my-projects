import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.Iterator;

public class Environment extends JPanel
{
	
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	
	
	public Environment(){
		Thread animationThread = new Thread(new Runnable() {
	        public void run() {
	            while (true) {
	                moveShapes();
	                try {Thread.sleep(10);} catch (Exception ex) {}
	            }
	        }
	    });
		
		 animationThread.start();
	}
	
	
	public void addCircle (Circle circle){
		circles.add(circle);
	}
	
	public Circle getCircle (int index){
		return (Circle) circles.get(index);
	}
	
	//collision detection for Axis Aligned Bounding Box
	public boolean AABBvsAABB( AABB a, AABB b )
	 {
	     // Exit with no intersection if found separated along an axis
		 if(a.getMax().getX() < b.getMin().getX() || a.getMin().getX() > b.getMax().getX()) return false;
		 if(a.getMax().getY() < b.getMin().getY() || a.getMin().getY() > b.getMax().getY()) return false;
	  
		 // No separating axis found, therefore there is at least one overlapping axis
		 return true;
	 }
	
	
	public static double Distance( Vector a, Vector b )				
	{
		//find the distance between two vectors
		double sqrX = (a.getX() - b.getX()) * (a.getX() - b.getX());
		double sqrY = (a.getY() - b.getY()) * (a.getY() - b.getY());
		
		return Math.sqrt( sqrX + sqrY);
	}
	
	//collision detection for circle
	public static boolean CirclevsCircleUnoptimized( Circle a, Circle b )
	{
	  double r = a.getRadius() + b.getRadius();
	  return r >= Distance( a.getPosition(), b.getPosition() );
	}
	
	
	
	/*boolean CirclevsCircle( Manifold m )
	{
	  // get the two circles from the collison
	  Circle A = (Circle)m.getA();
	  Circle B = (Circle)m.getB();
	 
	  // Normal = Vector from A to B
	  Vector n = B.getPosition().minus( A.getPosition());
	 
	  float r = (float) (A.getRadius() + B.getRadius());
	  r *= r;		//absolute value
	  
	  //needs to be optomized: take out the square roots i.e Distance().....
	  float d = (float) Distance(A.getPosition(), B.getPosition());   //d = distance between
	 
	  if(d > r){		///perform distance without sqrt
		  return false;
	  }
	 
	  // Circles have collided, now compute manifold			
	  // If distance between circles is not zero
	  if(d != 0)
	  {
	    // penetration depth is difference between radius and distance
	    m.setPenetration(r - d);
	 
	    // Utilize our d since we performed sqrt on it already within Length( )
	    // Points from A to B, and is a unit vector
	    
	    m.setNormal(n.times(d));						//Velocity 
	    
	    m.ResolveCollision(A, B);
	    return true;
	  } else // Circles are on same position
	  {
	    // Choose random (but consistent) values
	    m.setPenetration((float)A.getRadius());
	    double[] a = {1.0, 0.0};
	    m.setNormal(new Vector( a ));
	    
	    m.ResolveCollision(A, B);
	    return true;
	  }
	}*/
	
	
	public void moveShapes(){											//need to rename
		for (int i = 0; i < circles.size(); i++) {
			
			Vector currentPos = (getCircle(i).getPosition());   //why this no work??
			//System.out.println("circle "+ i + " = " + currentPos);
			Vector velocity = getCircle(i).getVelocity();
			
			currentPos = currentPos.plus(velocity);	
			getCircle(i).setPosition(currentPos);
			repaint();		
			
			boolean collision = CirclevsCircleUnoptimized(getCircle(0), getCircle(1));
			System.out.println(collision);
		}
					//   CP' = CP' + (D * (S * deltaTime))
	}	

	public void paintComponent(Graphics g)
	{
		//clear graphic
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for (int i = 0; i < circles.size(); i++) {
			
			double x = getCircle(i).getX();
			double y = (int)getCircle(i).getY();
			double r = (int)getCircle(i).getRadius();
			
			g.drawOval((int)x*20, (int)y*20, (int)r*20, (int)r*20);
			//g.fillOval(x*20, y*20, r*20, r*20);
			
		}
		
	}
	
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		Environment environment = new Environment();
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.add(environment);
		frame.pack();
		frame.setVisible(true);
		
		double[] posOne = {20, 10.0 };				//x and y coordinates
	    double[] posTwo = {2 , 10.0};				//x and y coordinates
	    double[] posThree = {-1, 0};
	    double[] posFour = {1, 0};

		Circle circle_1 = new Circle(1.0, new Vector(posOne), 0.1);
		Circle circle_2 = new Circle(1.0, new Vector(posTwo), 0.1);

		environment.addCircle(circle_1);
		environment.addCircle(circle_2);
		
		circle_1.setDirection(new Vector(posThree));
		circle_2.setDirection(new Vector(posFour));
		
	}

}
