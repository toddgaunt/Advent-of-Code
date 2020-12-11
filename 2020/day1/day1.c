#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 1024

int compare(const void *a, const void *b) {
	return *(int*)a - *(int*)b;
}

int read_input(int numbers[MAX], char const *filepath)
{
	// Open the input file
	FILE *fp = fopen(filepath, "ro");
	if (fp == NULL) {
		fprintf(stderr, "error: couldn't open $s\n", filepath);
		exit(EXIT_FAILURE);
	}

	// Allocate space for the numbers from the file, and read in each line
	int count = 0;
	for (int i = 0; i < 1024; i++) {
		char buf[1024];
		fgets(buf, 1024, fp);
		// No more content in the file to read
		if (strlen(buf) == 0) {
			break;
		}
		numbers[i] = atoi(buf);
		count++;
	}
	fclose(fp);

	return count;
}

void part1()
{
	int numbers[MAX];
	int count = read_input(numbers, "input");
	qsort(numbers, count, sizeof(*numbers), compare);

	int left = 0;
	int right = count - 1;
	// Find the two numbers that add up to 2020
	while (left < count && right >= 0) {
		if (numbers[left] + numbers[right] == 2020) {
			break;
		}

		if (numbers[left] + numbers[right] < 2020) {
			left++;
		} else {
			right--;
		}
	}

	if (left >= count || right < 0) {
		printf("couldn't find two numbers that add up to 2020\n");
	} else {
		printf("%d\n", numbers[left] * numbers[right]);
	}
}

void part2()
{
	int numbers[MAX];
	int count = read_input(numbers, "input");
	qsort(numbers, count, sizeof(*numbers), compare);

	int base;
	int left;
	int right;
	for (base = 0; base < count - 1; base++) {
		left = base;
		right = count - 1;
		// Find the two numbers that add up to 2020
		while (left < count && right >= 0) {
			if (numbers[base] + numbers[left] + numbers[right] == 2020) {
				goto end;
			}

			if (numbers[base] + numbers[left] + numbers[right] < 2020) {
				left++;
			} else {
				right--;
			}
		}
	}
end:
	printf("%d\n", numbers[base] * numbers[left] * numbers[right]);
}

int main(int argc, char **argv)
{
	part1();
	part2();
}
