import java.util.*;

//returns kth largest int in an array, accounting for dups.

class KthLargest {
   private List<Integer> sorted;
   private int k;
   private int acctForDups;

   public KthLargest(int k, int[] nums) {
      this.k = k;
      sorted = new ArrayList<>();
      Arrays.sort(nums);
      for (int i :nums)
      {
         sorted.add(i);
      }
   }
   //returns kth largest integer in arrayList accounting for dups
   public int getKthLargest()throws NoKthLargestValueException
   {
      Set<Integer> set = new HashSet<>();
      acctForDups = 0;

      //accounts for dups
      for (int i = sorted.size() - 1; i >= sorted.size() - k - acctForDups; i--)
      {
         if (set.contains(sorted.get(i)))
         {
            acctForDups++;
            if (sorted.size() - k - acctForDups < 0)
            {
               throw new NoKthLargestValueException("");
            }
         }
         set.add(sorted.get(i));
      }
      return sorted.get(sorted.size() - k - acctForDups);
   }
   //add int into sorted arrayList
   public void add(final int VAL){
      boolean valAdded = false;

      for (int i = 0; i < sorted.size(); i++)
      {
         if (VAL <= sorted.get(i))
         {
            sorted.add(i, VAL);
            valAdded = true;
            break;
         }
      }
      if (!valAdded)
      {
         sorted.add(VAL);
      }
   }

   //helper to produce initial array;
   private static int[] getRandomArray(final int SIZE, final int BOUND)
   {
      int[] nums = new int[SIZE];
      Random ran = new Random();

      for (int i = 0; i < SIZE; i++)
      {
         nums[i] = ran.nextInt(BOUND ) + 1;
      }
      return nums;
   }

   @Override
   public String toString()
   {
      StringBuilder builder = new StringBuilder();

      sorted.forEach(num -> {builder.append(num); builder.append(" ");});
      builder.append("\n");
      builder.append("Array size: ");
      builder.append(sorted.size());
      builder.append("\n");
      builder.append(stringHelper(k));
      builder.append(" highest value : ");
      try
      {
         builder.append(getKthLargest());
      } catch (NoKthLargestValueException e)
      {
         builder.append(e.getMessage());
      }
      builder.append("\n");
      return builder.toString();
   }
   //helper for toString method, replaces k with String with proper post-fix
   private String stringHelper(int k)
   {
      switch(k){
         case 1: return "1st";
         case 2: return "2nd";
         case 3: return "3rd";
         default: return k + "th";
      }
   }

   public static void main(String[] args)
   {
      final int INIT_ARRAY_SIZE = 30;
      final int INIT_ARRAY_BOUND = 20;
      int kthVal = 20;

      Random ran = new Random();

      KthLargest solution = new KthLargest(kthVal, getRandomArray(INIT_ARRAY_SIZE, INIT_ARRAY_BOUND));

      System.out.println(solution.toString());

      //add to arrayList and output
      for (int i = 0; i < 50; i++)
      {
         solution.add(ran.nextInt(INIT_ARRAY_BOUND + 10));
         System.out.println(solution.toString());
      }

      //test add
      solution = new KthLargest(2, new int[]{5,5,5,5,5});
      boolean gotValue = false;
      //expect error and then 4, no 2nd highest due to array filled with 1 val
      do
      {
         try
         {
            System.out.println(solution.getKthLargest());
            gotValue = true;
         } catch (NoKthLargestValueException e)
         {
            System.out.println(e.getMessage());
            solution.add(4);
         }
      }while (!gotValue);

   }
}

class NoKthLargestValueException extends Exception
{
   NoKthLargestValueException(String num)
   {
      super("doesn't exist due to duplicates.");
   }
}