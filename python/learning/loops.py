def triangle_left():    
    inner_range=8
    for i in range(8):
        print("")        
        for j in range(inner_range):
            print('#',end="")
        inner_range-=1

def triangle_right():
    inner_range=8
    spaces=0
    for i in range(8):
        print("")        
        for h in range(spaces):
            print(" ",end="")
        for j in range(inner_range):
            print('#',end="")
        inner_range-=1
        spaces+=1
            
def triangle_top():
    loop_range=8
    inner=8
    spaces=0
    for i in range(loop_range):
        print("")
        for j in range(inner):
            print("#",end="")
        for j in range(spaces):
            print(" ",end="")
        for j in range(inner):
            print("#",end="")
        inner-=1
        spaces+=2

def t_br():
    rows=8
    hashes=1
    spaces=7
    for i in range(rows):
        print("")
        for j in range(spaces):
            print(" ", end="")
        for j in range(hashes):
            print("#", end="")
        hashes+=1
        spaces-=1

def t_bl():
    rows=8
    hashes=1
    for i in range(rows):
        print("")
        for j in range(hashes):
            print("#",end="")
        hashes+=1

def triangle_bot():
    rows=8
    hashes=1
    spaces=rows*2 - 2
    for i in range(rows):
        print("")
        for j in range(hashes):
            print("#", end="")
        for j in range(spaces):
            print(" ", end="")
        for j in range(hashes):
            print("#", end="")
        spaces-=2
        hashes+=1
    

triangle_right()
triangle_left()
t_bl()
t_br()
triangle_top()
triangle_bot()



