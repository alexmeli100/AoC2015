fn main() {
    part1(29000000);
    part2(29000000);
}

fn part1(input: usize) {
  let mut house = vec![10; input];

  for i in 2..input/10 {
    for j in (i..input/10).step_by(i) {
      house[j] += i * 10;
    }

    if house[i] >= input {
      println!("{}", i);
      break;
    }
  }
}

fn part2(input: usize) {
  let mut house = vec![0; input];

  for i in 1..input/10 {
    let mut count = 0;

    for j in (i..input/10).step_by(i) {
      if count >= 50 { break; }

      house[j] += i * 11;
      count += 1;
    }

    if house[i] >= input {
      println!("{}", i);
      break;
    }
  }
}