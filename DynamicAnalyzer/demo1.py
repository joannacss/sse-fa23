from examples.triangle import triangle
import sys

def analyze(frame, event, arg):
	# get function code
    
    # get function name
    
    # get line number
    
    # prints them f"{event} --> {function_name}:{lineno}"
    
    # returns the function itself to track the new scope
    
    

def main():
    # start tracing the invocation
    
    output = triangle(2, 2, 1)
    
    # stop tracing the invocation
    

    print("Output at main()", output)
    

if __name__ == '__main__':
    main()