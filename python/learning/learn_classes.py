class Animal():

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def announce(self):
        print("Hi, I am {0}, and I am {1} years old!".format(self.name, self.age))

class Dog(Animal):

    def __init__(self, name, age, chases_cats):
        Animal.__init__(self, name, age)
        self.chases_cats = chases_cats
        self.announce()

    def bark(self):
        print("Woof woof!")

def main():
    scruffy = Dog("Scruffy", 3, True)
    if(scruffy.chases_cats):
        scruffy.bark()

main()
