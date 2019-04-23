#[macro_use]
extern crate lazy_static;
extern crate regex;
extern crate serde_json;

use regex::Regex;
use std::fs::File;
use std::io::Read;
use serde_json::{Error, Map, Number, Value};

fn main () {
  let mut f = File::open("input.txt").expect("Error opening file");
  let mut input = String::new();
  f.read_to_string(&mut input)
    .expect("Error reading file");

  let json_input = serde_json::from_str(&input).unwrap();

  println!("{}", part1(&input));
  println!("{}", part2(&json_input));
}

fn part1(doc: &str) -> i32 {
  lazy_static! {
    static ref RE: Regex = Regex::new(r"[-]?\d+").unwrap();
  }

  RE.captures_iter(doc)
    .map(|d| d.get(0).unwrap()
      .as_str().parse::<i32>().unwrap())
    .sum()
}

fn part2(v: &Value) -> i64 {
  match *v {
      Value::Null | Value::Bool(_) | Value::String(_) => 0,
      Value::Number(ref n) => n.as_i64().expect("not an i64"),
      Value::Array(ref vec) => {
          let mut s = 0;
          for val in vec {
              s += part2(val);
          }
          s
      },
      Value::Object(ref m) => {
          let mut s = 0;
          for v in m.values() {
              match *v {
                  Value::String(ref v_str) => {
                      if v_str == "red" {
                          return 0;
                      }
                  },
                  _ => s += part2(v)
              }
          }
          s
      }
  }
}
