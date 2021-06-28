import Foundation

enum Instruction {
    case HLF(String)
    case TPL(String)
    case INC(String)
    case JMP(Int)
    case JIE(String, Int)
    case JIO(String, Int)
}

func loadInstructions(_ fname: String) -> [Instruction] {
    let input = try! String(contentsOfFile: fname, encoding: String.Encoding.utf8)
    let intrs = input.components(separatedBy: "\n")
    var instructions: [Instruction] = []

    for ins in intrs {
        let s = ins.split(separator: " ", maxSplits: 1)

        switch s[0] {
        case "hlf":
            instructions.append(Instruction.HLF(String(s[1])))
        case "tpl":
            instructions.append(Instruction.TPL(String(s[1])))
        case "inc":
            instructions.append(Instruction.INC(String(s[1])))
        case "jmp":
            instructions.append(Instruction.JMP(Int(s[1])!))
        case "jie":
            let sp = s[1].components(separatedBy: ", ")
            instructions.append(Instruction.JIE(sp[0], Int(sp[1])!))
        case "jio":
            let sp = s[1].components(separatedBy: ", ")
            instructions.append(Instruction.JIO(sp[0], Int(sp[1])!))
        default:
            print("Unknown instruction")
            exit(1)
        }
    }

    return instructions
}

struct CPU {
    var pc = 0
    var registers = ["a": 0, "b": 0]
    var instructions: [Instruction]

    init(fname: String) {
        instructions = loadInstructions(fname)
    }

    mutating func setRegister(register name: String, to value: Int) {
        registers[name] = value
    }

    mutating func reset() {
        pc = 0

        for (reg, _) in registers {
            registers[reg] = 0
        }
    }

    mutating func run() {
        while pc >= 0 && pc < instructions.count {
            var dis = 1

            switch instructions[pc] {
            case .HLF(let r):
                registers[r]! /= 2
            case .TPL(let r):
                registers[r]! *= 3
            case .INC(let r):
                registers[r]! += 1
            case .JMP(let off):
                dis = off
            case .JIE(let r, let off):
                if registers[r]! % 2 == 0 {
                    dis = off
                }
            case .JIO(let r, let off):
                if registers[r]! == 1 {
                    dis = off
                }
            }

            pc += dis
        }
    }
}

func solve() {
    let args = CommandLine.arguments

    if args.count < 2 {
        print("Please provide a file name")
        exit(1)
    }

    var cpu = CPU(fname: args[1])
    cpu.run()

    print(NSString(format: "Part1: %d", cpu.registers["b"]!))

    cpu.reset()
    cpu.setRegister(register: "a", to: 1)
    cpu.run()

    print(NSString(format: "Part2: %d", cpu.registers["b"]!))
}



solve()