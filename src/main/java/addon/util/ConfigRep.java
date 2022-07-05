package addon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class ConfigRep {
	static List<Function> functions = new ArrayList<>();
	static List<Operator> operator = new ArrayList<>();
	
	static{
		functions.add(new Function("flip", 2) {
		    @Override
		    public double apply(double... args) {
		    	if((((int)args[0])/((int)args[1]))%2 == 1) {
		    		return args[1] - args[0]%args[1];
		    	}
		        return args[0]%args[1];
		    }
		});
		functions.add(new Function("rand", 2) {
			private Random random = new Random();
		    @Override
		    public double apply(double... args) {
		        return this.random.nextDouble() * (args[1] - args[0]) + args[0];
		    }
		});
		functions.add(new Function("min", 2) {
		    @Override
		    public double apply(double... args) {
		        return Math.min(args[0], args[1]);
		    }
		});
		functions.add(new Function("max", 2) {
		    @Override
		    public double apply(double... args) {
		        return Math.max(args[0], args[1]);
		    }
		});
		functions.add(new Function("if", 3) {
		    @Override
		    public double apply(double... args) {
		    	if(args[0] == 1) {
		    		return args[1];
		    	}
		    	return args[2];
		    }
		});
		operator.add(new Operator(">", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

	        @Override
	        public double apply(double[] values) {
	            if (values[0] > values[1]) {
	                return 1d;
	            } else {
	                return 0d;
	            }
	        }
		});
		operator.add(new Operator("<", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

	        @Override
	        public double apply(double[] values) {
	            if (values[0] < values[1]) {
	                return 1d;
	            } else {
	                return 0d;
	            }
	        }
		});
		operator.add(new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

	        @Override
	        public double apply(double[] values) {
	            if (values[0] >= values[1]) {
	                return 1d;
	            } else {
	                return 0d;
	            }
	        }
		});
		operator.add(new Operator("<=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

	        @Override
	        public double apply(double[] values) {
	            if (values[0] <= values[1]) {
	                return 1d;
	            } else {
	                return 0d;
	            }
	        }
		});
		operator.add(new Operator("==", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

	        @Override
	        public double apply(double[] values) {
	            if (values[0] == values[1]) {
	                return 1d;
	            } else {
	                return 0d;
	            }
	        }
		});
		operator.add(new Operator("!=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

	        @Override
	        public double apply(double[] values) {
	            if (values[0] != values[1]) {
	                return 1d;
	            } else {
	                return 0d;
	            }
	        }
		});
	}
	
	public static Vector rep_Vector(String s,double time) {
		Vector vt = new Vector();
		String[] rep = getV(s);
		
		
		vt = new Vector(rep(rep[0],time),rep(rep[1],time),rep(rep[2],time));
		return vt;
	}
	
	public static String[] getV(String s) {
		String str = s;
		String vector[] = new String[3];
		
		if(str.split(",").length > 2) {
			for(int l = 0; l <= s.split(",").length;l++) {
				int i = str.indexOf("(");
				int j = str.indexOf(")");
				int k = str.indexOf(",");
				if(i > -1 && j >-1 && k>-1 && i < k && k < j) {
					str = str.replaceFirst("\\(", "\\[");
					str = str.replaceFirst("\\)", "\\]");
					str = str.replaceFirst(",", "`");
				} else {
					str = str.replaceFirst(",", "※");
				}
			}
			vector[0] = str.split("※")[0].replace("\\[", "\\(").replace("\\]", "\\)").replace("`", ",");
			vector[1] = str.split("※")[1].replace("\\[", "\\(").replace("\\]", "\\)").replace("`", ",");
			vector[2] = str.split("※")[2].replace("\\[", "\\(").replace("\\]", "\\)").replace("`", ",");
		} else {
			vector = str.split(",",3);
		}
		return vector;
	}
	
	public static double rep(String equation,double time) {
		double result = new ExpressionBuilder(equation)
				.variables("t")
				.functions(functions)
				.operator(operator)
				.build()
				.setVariable("t", time)
				.evaluate();
		return result;
	}
}
