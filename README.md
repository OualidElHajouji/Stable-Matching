# Stable-Matching
The goal is to compute a stable matching between a set of n women and a set of n men using the Gale-Shapley algorithm

Each man is to marry one woman and each woman is to marry one man. Men are divided
into m groups and similarly women are divided into w groups, with groups being possibly
of different sizes. The sizes of all groups are given in menGroupCount and . Within each group, all individuals
are alike: they are all equally attractive in the eyes of any person of the other gender, and
they all have identical preference ordering with respect to groups of the other gender. The
problem is essentially the same as the original stable matching task, but it allows us to
represent preference orderings more compactly by a pair of matrices of dimensions m × w
and w × m (representing the ordering of preferences for the groups of men and women,
respectively) instead of a pair of n × n matrices (representing the ordering of preferences
between individuals).
A matching is considered stable if there does not exist a man and a woman who would
both (strictly) prefer to be with each other than with the partner they are currently married
to. (It is sometimes said that such a matching is “weakly” stable.) The output of your
program should be an integer matrix M of dimension m × w, with entry Mi,j denoting
the number of men from the i-th group of men married to women from the j-th group of
women.