import time

def visited(input):
  x, y = 0, 0
  seen = {(0, 0)}

  for direction in input:
    if (direction == '^'):
      y += 1
    elif (direction == 'v'):
      y -= 1
    elif (direction == '>'):
      x += 1
    elif (direction == '<'):
      x -= 1
    
    if ((x, y) in seen):
        continue
    
    seen.add((x, y))

  return seen

def part1(input):
  return len(visited(input))

def part2(input):
  santa = [input[i] for i in range(len(input)) if i % 2 == 0]
  robo_santa = [input[i] for i in range(len(input)) if i % 2 != 0]
  santa_visited = visited(santa)
  r_santa_visited = visited(robo_santa)

  return len(santa_visited.union(r_santa_visited))

with open("input.txt") as input:
  directions = input.read()

print(part1(directions))
print(part2(directions))


