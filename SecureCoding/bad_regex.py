import re
import time
# regex = "(a+)+b"
regex = r"^(a+)+$"
# string = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
# re.match(regex, string)


for i in range(4, 32, 2):
	start = time.time()
	re.match(regex, "a"*i+"X") # 2^i possible paths	
	print(i,time.time()-start, sep="\t")

# re.match(regex, "a"*16+"X") # 2^16 possible paths
# re.match(regex, "a"*16+"X") # 2^16 possible paths
# re.match(regex, "a"*32+"X") # 2^32 possible paths