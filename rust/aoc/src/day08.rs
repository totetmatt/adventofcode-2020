use std::collections::HashSet;
use std::fs;
pub fn day08() {
    enum Op {
        Jmp(i32),
        Nop(i32),
        Acc(i32),
        Err(),
    }
    enum Run {
        Ok(i32),
        Error(i32),
    }
    let contents =
        fs::read_to_string("..\\..\\input08").expect("Something went wrong reading the file");
    let prog = contents
        .split("\r\n")
        .map(|x| {
            let mut r = x.chars();
            let rr = r.by_ref();
            let k = rr.take_while(|&x| x != ' ').collect::<String>();
            let v = rr.collect::<String>().parse::<i32>().unwrap();
            match k.as_str() {
                "jmp" => Op::Jmp(v),
                "nop" => Op::Nop(v),
                "acc" => Op::Acc(v),
                _ => Op::Err(),
            }
        })
        .collect::<Vec<Op>>();
    fn run(
        prog: &Vec<Op>,
        pointer: i32,
        accumulator: i32,
        visited: &HashSet<usize>,
        changed: bool,
    ) -> Run {
        let pointer: i32 = pointer;
        let accumulator: i32 = accumulator;
        let mut visited: HashSet<usize> = visited.iter().copied().collect();
        if !visited.contains(&(pointer as usize)) && (pointer as usize) < prog.len() {
            visited.insert(pointer as usize);
            return match prog[pointer as usize] {
                Op::Jmp(i) => match run(prog, pointer + i, accumulator, &visited, changed) {
                    Run::Error(i) => {
                        if changed {
                            Run::Error(i)
                        } else {
                            run(prog, pointer + 1, accumulator, &visited, true)
                        }
                    }
                    Run::Ok(i) => Run::Ok(i),
                },
                Op::Nop(i) => match run(prog, pointer + 1, accumulator, &visited, changed) {
                    Run::Error(_) => {
                        if changed {
                            Run::Error(i)
                        } else {
                            run(prog, pointer + i, accumulator, &visited, true)
                        }
                    }
                    Run::Ok(i) => Run::Ok(i),
                },
                Op::Acc(i) => run(prog, pointer + 1, accumulator + i, &visited, changed),
                Op::Err() => Run::Error(-1),
            };
        } else {
            if (pointer as usize) < prog.len() {
                Run::Error(accumulator)
            } else {
                Run::Ok(accumulator)
            }
        }
    }
    match run(&prog, 0, 0, &HashSet::<usize>::new(), false) {
        Run::Ok(i) => println!("OK {:?}", i),
        Run::Error(i) => println!("Error {:?}", i),
    }
}
