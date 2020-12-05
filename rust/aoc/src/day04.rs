use std::collections::HashSet;
use std::fs;

pub fn day04() {
    fn parse_check(s: String) -> i32 {
        let valid_keys: HashSet<String> = [
            String::from("byr"),
            String::from("iyr"),
            String::from("eyr"),
            String::from("hgt"),
            String::from("hcl"),
            String::from("ecl"),
            String::from("pid"),
        ]
        .iter()
        .cloned()
        .collect::<HashSet<_>>();
        let mut string_iterator = s.chars().peekable();
        let mut valid_key_count = 7;
        let mut is_value_valid = true;
        while string_iterator.peek().is_some() {
            let key = string_iterator
                .by_ref()
                .take_while(|&x| x != ':')
                .collect::<String>();
            let value = string_iterator
                .by_ref()
                .take_while(|&x| x != ' ')
                .collect::<String>();
            if valid_keys.contains(&key) {
                valid_key_count -= 1;
                match &key[..] {
                    "byr" => {
                        is_value_valid &= 1920 <= value.as_str().parse::<i32>().unwrap()
                            && value.as_str().parse::<i32>().unwrap() <= 2002
                    }
                    "iyr" => {
                        is_value_valid &= 2010 <= value.as_str().parse::<i32>().unwrap()
                            && value.as_str().parse::<i32>().unwrap() <= 2020
                    }
                    "eyr" => {
                        is_value_valid &= 2020 <= value.as_str().parse::<i32>().unwrap()
                            && value.as_str().parse::<i32>().unwrap() <= 2030
                    }
                    "hgt" => {}
                    "hcl" => {}
                    "ecl" => match value.as_str() {
                        "amb" | "blu" | "brn" | "gry" | "grn" | "hzl" | "oth" => (),
                        _ => is_value_valid = false,
                    },
                    "pid" => {
                        is_value_valid &= value
                            .chars()
                            .by_ref()
                            .take_while(|&x| x.is_digit(10) == false)
                            //.collect()
                            .len()
                            == 0
                    }
                    _ => (),
                }
            }
        }
        if valid_key_count <= 0 && is_value_valid {
            1
        } else {
            0
        }
    }
    let contents =
        fs::read_to_string("..\\..\\input04").expect("Something went wrong reading the file");
    let map: i32 = contents
        .split("\n\n")
        .map(|x| parse_check(x.replace("\n", " ")))
        .collect::<Vec<_>>()
        .iter()
        .sum();

    println!("{:?}", map);
}
