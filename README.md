A software implementation of the strategy board-game Risk. Project for UCD COMP20050 Software Engineering Project II.

Reinforcement

    For placing reinforcements, the bot chooses the most vulnerable country that shares a border with the enemy. If none can
    be found, it then checks for borders with neutral territories.

    For Neutrals:

        When reinforcing on behalf of neutral players, the bot prioritises placing reinforcements in territories that share
        borders with its hostile enemies.

    Trading in Cards:

        The bot refrains from trading in cards until forced to do so

Attacking

    When attacking, the bot compiles a list of all possible attacks, and evaluates the value of a particular country based on
    the probability of a successful attack, the continent it is part of, and the percentage of that continent that it and its
    enemy each control. These attacks are then sorted by their new values and executed sequentially until no attacks remain
    that cross a given probability threshold. If victorious, it will move 2/3 of the attacking country's remaining troops forward
    to the newly conquered one.

Defending

    The bot will always use the maximum number of available troops to defend.

Fortification:

    When fortifying, the bot uses the attack evaluation algorithm in reverse, to assess where it is most vulnerable. It also
    finds the strongest country with no enemy borders, and tries to move all but one troop to the most vulnerable. If they
    are not connected, it cycles through the vulnerable countries in order of decreasing risk until a connection is found.
