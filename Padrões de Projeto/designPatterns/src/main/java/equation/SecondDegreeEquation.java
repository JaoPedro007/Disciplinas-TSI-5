package equation;

public class SecondDegreeEquation {

	private float a;
	private float b;
	private float c;

	public SecondDegreeEquation(float a, float b, float c) {
		this.a = a;
		this.b = b;
		this.c = c;

		if (Math.abs(0.0f - a) <= 0.000_000_1) {
			throw new InvalidSecondDregreeEquationException("Invalid parameter 'a' " + a);
		}
	}

	public final float getA() {
		return a;
	}

	public final float getB() {
		return b;
	}

	public final float getC() {
		return c;
	}

	public boolean hasRealSolution() {
		float delta = b * b - 4 * a * c;

		if (delta < 0)
			return false;

		return true;
	}
	
	public int howManyRealSolutions() {
	    float delta = b * b - 4 * a * c;

	    if (delta > 0)
	        return 2;
	    
	    else if (delta == 0)
	        return 1;
	    
	    else
	        return 0;
	}
	
	public float[] getRealSolutions() {
	    float delta = b * b - 4 * a * c;

	    if (delta < 0) {
	        return new float[0];
	    }
	    else if (delta == 0) {
	        float x = -b / (2 * a);
	        return new float[]{x};
	    }
	    else {
	        float x1 = (-b + (float) Math.sqrt(delta)) / (2 * a);
	        float x2 = (-b - (float) Math.sqrt(delta)) / (2 * a);
	        return new float[]{x1, x2};
	    }
	}




}
