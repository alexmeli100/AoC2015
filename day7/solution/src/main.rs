use std::collections::HashMap;
use std::io::prelude::*;
use std::fs::File;
use std::io::BufReader;
use std::time::{Instant};

#[derive(Debug, Clone, Copy, Eq, PartialEq)]
pub enum Op {
    OpAnd, OpOr, OpNot, OpLShift, OpRShift, OpStore, OpError
}

impl Op {
    pub fn apply_op(&self, lhs: u16, rhs: u16) -> u16 {
        match *self {
            Op::OpAnd => lhs & rhs,
            Op::OpOr => lhs | rhs,
            Op::OpLShift => lhs << rhs,
            Op::OpRShift => lhs >> rhs,
            Op::OpNot => !lhs,
            Op::OpStore => lhs,
            Op::OpError => panic!("No implementation for op")
        }
    }

    pub fn stoop(s: &str) -> Op {
        match s {
            "AND" => Op::OpAnd,
            "OR" => Op::OpOr,
            "LSHIFT" => Op::OpLShift,
            "RSHIFT" => Op::OpRShift,
            _ => Op::OpError
        }
    }
}

#[derive(Debug, Clone, Eq, PartialEq)]
pub struct Gate {
    op: Op,
    lhs: String,
    rhs: String,
    memoized: bool,
    val: u16
}

impl Gate {
    pub fn new() -> Gate {
        Gate {
            op: Op::OpError,
            lhs: String::from(""),
            rhs: String::from(""),
            memoized: false,
            val: 0
        }
    }
}

#[derive(Debug, Default, Clone)]
pub struct Circuit {
    circuits: HashMap<String, Gate>
}

impl Circuit {
    pub fn new() -> Circuit {
        Circuit {circuits: HashMap::new()}
    }

    fn output_of(&mut self, num_or_wire: &str) -> u16 {
        if num_or_wire.is_empty() {
            return 0;
        }

        match num_or_wire.parse::<u16>() {
            Ok(num) => num,
             Err(_) => self.find_output(num_or_wire)
        }
    }

    pub fn find_output(&mut self, wire: &str) -> u16 {
        let mut g = self.circuits.get_mut(wire).unwrap().clone();

        if g.memoized {
            return g.val;
        }

        let res = g.op.apply_op(self.output_of(&g.lhs), self.output_of(&g.rhs));

        g.memoized = true;
        g.val = res;
        self.circuits.insert(wire.to_string(), g);
        res
    }
}

fn main() {
    let now = Instant::now();
    let mut c = Circuit::new();
    let f = File::open("input.txt").expect("Error opening file");
    let r = BufReader::new(f);

    for line in r.lines() {
        let mut g = Gate::new();
        let l = line.expect("Error");
        let elem: Vec<&str> = l.split_whitespace().collect();

        if elem.len() == 3 {
            g.op = Op::OpStore;
            g.lhs = elem[0].to_string();
            c.circuits.insert(elem[2].to_string(), g);
        } else if elem[0] == "NOT" {
            g.op = Op::OpNot;
            g.lhs = elem[1].to_string();
            c.circuits.insert(elem[3].to_string(), g);
        } else {
            g.op = Op::stoop(elem[1]);
            g.lhs = elem[0].to_string();
            g.rhs = elem[2].to_string();
            c.circuits.insert(elem[4].to_string(), g);
        }
    }

    println!("{:?}", c.find_output("a"));


    println!("{:?}", Instant::now().duration_since(now));
}

