import java.util.Stack;


public class ExpressionTree {
	TreeNode root;
	
	public static void main(String[] args) {
		ExpressionTree tree = new ExpressionTree("a");
	}
	
	public ExpressionTree(String exp) {
		
		root = new TreeNode("^");
		root.left = new TreeNode("^");
		root.left.left = new TreeNode("2");
		root.left.right = new TreeNode("1");
		root.right = new TreeNode("^");
		root.right.left = new TreeNode("2");
		root.right.right = new TreeNode("+");
		root.right.right.left = new TreeNode("1");
		root.right.right.right = new TreeNode("3");
		
		buildTree(exp);
		printTree();
		System.out.println(solveTree(root));
	}
	
	private void buildTree(String exp) {
		
	}
	
	private void printTree() {
		System.out.println(printPre(root));
		Stack<String> stack = new Stack<String>();
		System.out.println(printIn(root, stack));
		System.out.println(printPost(root));
	}
	
	private String printPre(TreeNode cur) {
		String exp;
		if (cur.left != null) {
			exp = cur.val + " " + printPre(cur.left) + " " + printPre(cur.right);
		} else {
			exp = cur.val;
		}
		return exp;
	}
	
	private String printIn(TreeNode cur, Stack<String> stack) {
		String exp;
		if (cur.left != null) {
			if (!stack.isEmpty()) {
				if (checkOpPrec(cur.val, stack.peek())) {
					stack.push(cur.val);
					exp = printIn(cur.left, stack) + " " + cur.val + " " + printIn(cur.right, stack);
				} else {
					stack.push(cur.val);
					exp = "(" + printIn(cur.left, stack) + " " + cur.val + " " + printIn(cur.right, stack) + ")";
				}
				stack.pop();
			} else {
				stack.push(cur.val);
				exp = printIn(cur.left, stack) + " " + cur.val + " " + printIn(cur.right, stack);
			}
		} else {
			exp = cur.val;
		}
		return exp;
	}
	
	private String printPost(TreeNode cur) {
		String exp;
		if (cur.left != null) {
			exp = printPost(cur.left) + " " + printPost(cur.right) + " " + cur.val;
		} else {
			exp = cur.val;
		}
		return exp;
	}
	
	private double solveTree(TreeNode cur) {
		if (cur.left != null) {
			switch (cur.val) {
				case "+": return solveTree(cur.left) + solveTree(cur.right);
				case "-": return solveTree(cur.left) - solveTree(cur.right);
				case "*": return solveTree(cur.left) * solveTree(cur.right);
				case "/": return solveTree(cur.left) / solveTree(cur.right);
				default: return Math.pow(solveTree(cur.left), solveTree(cur.right));
			}
		} else {
			return Double.parseDouble(cur.val);
		}
	}
	
	private boolean checkOpPrec(String opIn, String opOut) {
		if (opOut.equals("+") || opOut.equals("-")) {
			return true;
		} else if (opOut.equals("*") || opOut.equals("/")) {
			if (opIn.equals("+") || opIn.equals("-")) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
