#!/bin/bash

guessed=0
not_guessed=0
total_attempts=0
guessed_numbers=()
attempts_history=()

while true; do
    # Генерация случайного числа от 0 до 9
    secret_number=$((RANDOM % 10))
    
    # Увеличение счетчика общего числа попыток
    ((total_attempts++))

    # Вывод приглашения пользователю
    echo -n "Шаг $total_attempts. Введите число от 0 до 9 (q для выхода): "
    read user_input

    # Проверка на выход
    if [ "$user_input" == "q" ]; then
        break
    fi

    # Проверка ввода на корректность
    if ! [[ "$user_input" =~ ^[0-9]$ ]]; then
        echo "Ошибка! Введите цифру от 0 до 9."
        continue
    fi

    # Добавление пары значений в историю
    if [ "$user_input" -eq "$secret_number" ]; then
        attempts_history+=("$user_input 1") # Угадано
        ((guessed++))
        echo -e "Угадано! Загаданное число: \e[32m$secret_number\e[0m"
    else
        attempts_history+=("$user_input 0") # Не угадано
        ((not_guessed++))
        echo -e "Промах! Моё число: \e[91m$secret_number\e[0m"
    fi

    # Вывод статистики
    hit_percentage=$((guessed * 100 / total_attempts))
    miss_percentage=$((not_guessed * 100 / total_attempts))
    echo "Попадание: ${hit_percentage}% Промах: ${miss_percentage}%"

    # Вывод последних 10 чисел
    echo -n "Последние 10 чисел: "
    for ((i=${#attempts_history[@]}-1; i>=0; i--)); do
        number=$(echo "${attempts_history[$i]}" | cut -d ' ' -f 1)
        result=$(echo "${attempts_history[$i]}" | cut -d ' ' -f 2)

        if [ "$result" -eq 1 ]; then
            echo -n -e "\e[32m$number\e[0m "
        else
            echo -n -e "\e[91m$number\e[0m "
        fi

        # Ограничение вывода только 10 числами
        if [ "$i" -eq "$((${#attempts_history[@]}-10))" ]; then
            break
        fi
    done
    echo

done

echo "Игра завершена."
