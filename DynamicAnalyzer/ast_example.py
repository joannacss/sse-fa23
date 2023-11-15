import inspect
import ast
from examples.twice import test

def main():
	test_source = inspect.getsource(test)
	test_ast = ast.parse(test_source)
	print(ast.dump(test_ast, indent=2))

if __name__ == '__main__':
	main()