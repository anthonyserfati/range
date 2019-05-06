This is a simple Range class
Range can be [a, b, c, .... ] : constructor will order params and determine min / max to set range borders
Border can include ('[') / exclude ('(')min / max border value
Left border is '[' or '('
Right border is ']' or ')'
Range constructor valiades params : not null, not empy, min expression length is 4, border must be '[', '(', '(', ')'
- toString return range formated string
- getters 
	- min : int value of range left border
	- minBorderVal : int value of range left border taking count border is included or excluded (can be min+1)
	- max : int value of range right border
	- maxBorderVal : int value of range right border taking count border is included or excluded (can be max-1)
	- borderMin : string value of left range border -> '[', '('
	- borderMax : string value of right range border -> ']', ')'

- equals returns true if range is equal to range passed as parameter
- hasCode : java standard method
	 
- contains(int[] values) returns true if all values are in ranger borders
- allPoints returns all ranger border taking count borders included / excluded
- containsRange(Range range) returns if range passed as parameter is inside range
- endPoints returns min and max border in a sorted array, taking count borders included / excluded
- overlapsRange returns true if range has interesection with range passed as parameter
