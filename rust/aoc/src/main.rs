extern crate regex;
use regex::Captures;
use regex::Regex;
use std::collections::HashSet;
use std::fs;
use std::str::Chars;
fn day01() {
    let contents = fs::read_to_string("input01").expect("Something went wrong reading the file");

    let mut data: Vec<i32> = contents
        .split("\r\n")
        .map(|x| x.parse::<i32>().unwrap())
        .collect();

    data.sort();
    println!("== Part 1 ==");

    for x in (1..data.len()).rev() {
        let last: i32 = data[x];
        let diff = 2020 - last;
        let found = data.binary_search(&diff);
        match found {
            Ok(x) => {
                println!(
                    "{:?} {:?} {:?} {:?}",
                    data[x],
                    last,
                    data[x] + last,
                    data[x] * last
                );
                break;
            }
            Err(_) => (),
        }
    }

    println!("== Part 2 ==");

    for x in (0..data.len()).rev() {
        let first_number: i32 = data[x];
        let first_diff = 2020 - first_number;
        let found = data[0..x].binary_search(&first_diff);
        match found {
            Ok(_) => continue,
            Err(xx) => {
                for z in 0..xx {
                    let diff2 = first_diff - data[z];
                    let sub = &data[0..z + 1];
                    let found2 = sub.binary_search(&diff2);
                    match found2 {
                        Ok(y) => {
                            println!(
                                "{:?} {:?} {:?} {:?} {:?}",
                                first_number,
                                data[z],
                                data[y],
                                first_number + data[z] + data[y],
                                first_number * data[z] * data[y]
                            );
                            break;
                        }
                        Err(_) => (),
                    }
                }
            }
        }
    }
}
fn day02() {
    fn countCharacters(s: &str, c: char) -> i32 {
        s.chars()
            .fold(0, |agg, x| if x == c { agg + 1 } else { agg })
    }
    fn part1Check(c: &Captures<'_>) -> i32 {
        let min = c.name("n1").unwrap().as_str().parse::<i32>().unwrap();
        let max = c.name("n2").unwrap().as_str().parse::<i32>().unwrap();
        let checkChar = c
            .name("charCheck")
            .unwrap()
            .as_str()
            .chars()
            .next()
            .unwrap();
        let password = c.name("password").unwrap().as_str();
        let count = countCharacters(password, checkChar);
        if min <= count && count <= max {
            1
        } else {
            0
        }
    }
    fn part2Check(c: &Captures<'_>) -> i32 {
        let n1: usize = c.name("n1").unwrap().as_str().parse::<usize>().unwrap() - 1;
        let n2: usize = c.name("n2").unwrap().as_str().parse::<usize>().unwrap() - 1;
        let checkChar = c
            .name("charCheck")
            .unwrap()
            .as_str()
            .chars()
            .next()
            .unwrap();
        let password: &str = c.name("password").unwrap().as_str();
        let mut sChar: HashSet<char> = HashSet::new();
        sChar.insert(password.as_bytes()[n1] as char);
        sChar.insert(password.as_bytes()[n2] as char);
        if sChar.contains(&checkChar) && sChar.len() == 2 {
            1
        } else {
            0
        }
    }
    let rg =
        Regex::new(r"(?P<n1>\d+)-(?P<n2>\d+) (?P<charCheck>[a-z]): (?P<password>[a-z]+)").unwrap();
    let contents =
        fs::read_to_string("..\\..\\input02").expect("Something went wrong reading the file");
    let t = rg.captures("9-12 s: wnsdfrtssllsfbsssccb");
    println!("{:?}", t);
    let q: &str = "azeaze";

    let data: i32 = contents
        .split("\r\n")
        .map(|x| part2Check(&rg.captures(x).unwrap()))
        .sum();
    println!("{:?}", data);
}
fn main() {
    day02();
}
