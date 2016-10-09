
public class Cord {
	public final float x;
	public final float y;
public Cord(float x,float y){
	this.x=x;
	this.y=y;
}

public boolean isCenter() {
	
	Boolean a = false;
	
	if (-20 < this.x && this.x < 20){
		if (130 < this.y && this.y < 170){
			a = true;
		}
	}
	return a;
	
}
}
