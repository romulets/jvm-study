/**
 * Problem 4-4. Receiver Roster
 * <p></p>
 * Coach Bell E. Check is trying to figure out which of her football receivers to put in her starting
 * lineup. In each game, Coach Bell wants to start the receivers who have the highest performance
 * (the average number of points made in the games they have played), but has been having trouble
 * because her data is incomplete, though interns do often add or correct data from old and new
 * games.
 * <p></p>
 * Each receiver is identified with a unique positive integer jersey number, and each game
 * is identified with a unique integer time. Describe a database supporting the following operations,
 * each in worst-case O(log n) time, where n is the number of games in the database at the time of
 * the operation. Assume that n is always larger than the number of receivers on the team.
 * <ul>
 *     <li>record(g, r, p) record p points for jersey r in game g</li>
 *     <li>clear(g, r) remove any record that jersey r played in game g</li>
 *     <li>ranked receiver(k) return the jersey with the kth highest performance</li>
 * </ul>
 */
package datastructures.exercises.receiverroster;