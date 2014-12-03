import locale

locale.setlocale(locale.LC_ALL,"")

def main():
    print("Welcome to EZBooks!")
    bank_balance = float(input("Enter your starting balance: "))
    savings = float(input("How much of that is savings? "))
    virtual_balance = bank_balance - savings
    print("Your virtual balance is " + locale.currency(virtual_balance, grouping=True))


main()

