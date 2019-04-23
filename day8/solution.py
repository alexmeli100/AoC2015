def solution():
  with open("input.txt") as f:
    lines = f.read()[:-1].split("\n")

  part1 = sum([len(s) - len(eval(s)) for s in lines])
  part2 = sum([2+s.count('\\') + s.count('"') for s in lines])

  return (part1, part2)

def main():
  part1, part2 = solution()

  print(f"Part1: {part1}\nPart2: {part2}")

if __name__ == "__main__":main()