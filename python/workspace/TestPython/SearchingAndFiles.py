file = open("super_villians.txt")

for line in file:
    line = line.strip()
    print(line)
    
file.close()
